package com.telusko;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Tourist API", version = "1.0", description = "API for managing tourists")
, servers = @Server(
		url = "http://localhost:8484",
		description = "This app is deployed on Local server"
))
public class TouristappGlobalEhApplication {

	public static void main(String[] args) {
		SpringApplication.run(TouristappGlobalEhApplication.class, args);
	}

}
