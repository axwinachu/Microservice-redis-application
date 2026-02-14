package com.example.microservice_redis_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MicroserviceRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRedisApplication.class, args);
	}

}
