package com.telusko.springboot5;

import com.telusko.springboot5.service.ServiceLayer;
import com.telusko.springboot5.web.WebLayer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Springboot5Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext container = SpringApplication.run(Springboot5Application.class, args);

		WebLayer web = container.getBean(WebLayer.class);
		web.method2();

		ServiceLayer service = container.getBean(ServiceLayer.class);
		service.method1();
	}

}
