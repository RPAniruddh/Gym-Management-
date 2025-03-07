package com.gym.management.eurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CentralServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(CentralServer1Application.class, args);
	}

}
