package com.telusko.datajpa2page;

import com.telusko.datajpa2page.entity.Product;
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

//		System.out.println(service.searchProductById(55));
//		List<Integer> ids = Arrays.asList(53, 54, 55);
//		service.searchProductsByIds(ids).forEach(System.out::println);

//		System.out.println(service.searchProductsByProduct(new Product("Mobile", "Electronics", 5000.0,20)));

		List<Integer> ids = Arrays.asList(52, 53);
//		System.out.println(service.deleteProductsByIdsCrud(ids));

		String status = service.deleteProductsByIds(ids);

	}

}
