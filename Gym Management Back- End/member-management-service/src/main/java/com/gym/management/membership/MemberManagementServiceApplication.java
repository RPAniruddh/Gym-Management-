package com.gym.management.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MemberManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberManagementServiceApplication.class, args);
	}

}
