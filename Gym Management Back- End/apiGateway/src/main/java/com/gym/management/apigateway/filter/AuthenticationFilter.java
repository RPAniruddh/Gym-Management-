package com.gym.management.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.gym.management.apigateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil util;

	public static class Config {
	}

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					String role = util.extractRolesFromToken(authHeader);
					String requestedPath = exchange.getRequest().getPath().toString();
					String method = exchange.getRequest().getMethod().name();

					if (!isAuthorized(role, requestedPath, method)) {
						return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
					}

				} catch (Exception e) {
					return handleUnauthorized(exchange.getResponse(), "Invalid token");
				}
			}
			return chain.filter(exchange);
		};
	}

	private boolean isAuthorized(String role, String path, String method) {
		if ("ADMIN".equalsIgnoreCase(role)) {
			return true;
		} else if ("USER".equalsIgnoreCase(role)) {
			if (path.matches("/members/get/\\d+") && "GET".equalsIgnoreCase(method)
					|| path.matches("/members/add") && "POST".equalsIgnoreCase(method)
					|| path.matches("/members/getByEmail/[^/]+") && "GET".equalsIgnoreCase(method)
					|| path.matches("/members/update/\\d+") && "PUT".equalsIgnoreCase(method)
					|| path.matches("/memberships/\\d+") && "GET".equalsIgnoreCase(method)
					|| path.matches("/fitness/workouts/.*")
					|| path.matches("/fitness/workouts/member/\\d+") && "DELETE".equalsIgnoreCase(method)
					|| path.matches("/fitness/workouts") && "POST".equalsIgnoreCase(method)
					|| path.matches("/fitness/exercises") && "GET".equalsIgnoreCase(method)) {
				return true;
			}
		}
		return false;
	}

	private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		return response.setComplete();
	}
}
