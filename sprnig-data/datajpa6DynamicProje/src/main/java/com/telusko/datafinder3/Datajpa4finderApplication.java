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

		service.searchByPriceLessThan(1200.0, ResultView3.class).forEach(p -> System.out.println(p.getProductName() + " : " + p.getQuantity()));


	}

}
