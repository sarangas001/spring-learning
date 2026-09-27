package com.telusko.datajpa1crud;

import com.telusko.datajpa1crud.entity.Product;
import com.telusko.datajpa1crud.service.IProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Datajpa1crudApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext container = SpringApplication.run(Datajpa1crudApplication.class, args);

		IProductService service = container.getBean(IProductService.class);

//		Product pd1 = new Product("Laptop", "Electronics", 75000.0, 20);
//
//		System.out.println(service.saveproduct(pd1));
//
//		List<Product> products = new ArrayList<>();
//		products.add(new Product("Mobile", "Electronics", 5000.0, 20));
//		products.add(new Product("Watch", "Accessories", 4500.0, 15));
//		products.add(new Product("Shoes", "Fashion", 500.0, 40));
//		products.add(new Product("Books", "Stationary", 000.0, 50));
//
//		service.saveMultipleProducts(products).forEach(System.out::println);

//		service.getAllProducts().forEach(System.out::println);

//		List<Integer> ids = Arrays.asList(2,4,1);
//		service.getAllProductsByIds(ids).forEach(System.out::println);

//		System.out.println(service.getProductById(4));

		System.out.println("Total number of products " + service.getTotalProductsCount());
		Boolean status = service.isProductAvailable(3);

		System.out.println(status ? "Product available" : "Product is not available");

	}

}
