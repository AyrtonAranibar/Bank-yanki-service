package com.bank.ayrton.yanki_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class YankiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(YankiServiceApplication.class, args);
	}

}
