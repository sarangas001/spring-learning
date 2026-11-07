package com.telusko;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "E-commerce API", version="1.0", description = "E-commerce application"
), servers = @Server(
		url="http://localhost:8080",
		description = "E-commerce Application"
))
public class ECommerceBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceBackApplication.class, args);
	}

}
