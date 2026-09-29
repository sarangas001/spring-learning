package com.telusko.datajpa2page;

import com.telusko.datajpa2page.service.IProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Datajpa1crudApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext container = SpringApplication.run(Datajpa1crudApplication.class, args);

		IProductService service = container.getBean(IProductService.class);

//		service.fetchProductInfoSorting(false, "price", "productName").forEach(System.out::println);

//		service.fetchProductByPagination(2,2,false, "price")
//				.forEach(System.out::println);

		service.fetchProductByPagination(4);


	}

}
