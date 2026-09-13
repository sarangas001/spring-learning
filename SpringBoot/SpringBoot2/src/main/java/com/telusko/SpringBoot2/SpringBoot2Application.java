package com.telusko.SpringBoot2;

import com.telusko.SpringBoot2.service.IGreeting;
import com.telusko.SpringBoot2.service.Telusko;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBoot2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext container = SpringApplication.run(SpringBoot2Application.class, args);

		IGreeting bean = container.getBean(IGreeting.class);

		System.out.println(bean.generateGreeting("Saranga"));

	}

}
