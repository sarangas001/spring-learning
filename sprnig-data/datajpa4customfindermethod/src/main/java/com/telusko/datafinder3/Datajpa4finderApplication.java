package com.telusko.datafinder3;

import com.telusko.datafinder3.service.IProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Datajpa4finderApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext container = SpringApplication.run(Datajpa4finderApplication.class, args);

		IProductService service = container.getBean(IProductService.class);

//		service.searchByPriceGreaterThan(550.0).forEach(System.out::println);
//		service.searchByPriceLessThan(5000.0).forEach(System.out::println);
//		service.searchByPriceBetween(500.0, 5000.0);

//		service.searchByCategoryEquals("Fashion").forEach(System.out::println);

		List<String> categories = Arrays.asList("Fashion", "Electronic");
		service.searchByCategoryInAndPriceBetween(categories, 450.0, 6000.0).forEach(System.out::println);

	}

}
