package com.telusko.datafinder3;

import com.telusko.datafinder3.service.IProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Datajpa5StaticPro {

	public static void main(String[] args) {

		ConfigurableApplicationContext container = SpringApplication.run(Datajpa5StaticPro.class, args);

		IProductService service = container.getBean(IProductService.class);


		service.searchByPriceLessThan(1200.5).forEach(p -> System.out.println(p.getCategory() + " - " + p.getProductName()));


	}

}
