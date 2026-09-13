package com.telusko.SpringBoo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBoo3Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext container = SpringApplication.run(SpringBoo3Application.class, args);

		Telusko bean = container.getBean(Telusko.class);

		bean.buyTheCourse();

	}

}
