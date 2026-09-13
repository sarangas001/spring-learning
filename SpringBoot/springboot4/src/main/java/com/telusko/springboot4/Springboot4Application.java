package com.telusko.springboot4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Springboot4Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext conatiner = SpringApplication.run(Springboot4Application.class, args);

		Alien bean = conatiner.getBean(Alien.class);

		Alien.method();
		bean.method2();

	}

}
