package com.telusko.datafinder3;

import com.telusko.datafinder3.service.IProductService;
import com.telusko.datafinder3.view.ResultView1;
import com.telusko.datafinder3.view.ResultView3;
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

//		service.findByCategory("Fashion").forEach(System.out::println);
//
//		service.findByPrice(350.5, 2500.5).forEach(p->System.out.println(p));
//
//		int rowAffected = service.updateThePriceByProductName(545.5, "Shoes");

//		System.out.println("Number of rows updated " + rowAffected);

		int rowAffected = service.insertTheProductInfo(45, "ps5", "Gaming", 55505.5, 4);
		System.out.println("Number of rows updated " + rowAffected);

	}

}
