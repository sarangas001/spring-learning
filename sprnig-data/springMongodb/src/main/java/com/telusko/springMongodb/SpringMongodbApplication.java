package com.telusko.springMongodb;

import com.telusko.springMongodb.dto.CustomerDTO;
import com.telusko.springMongodb.service.CustomerService;
import com.telusko.springMongodb.util.IDGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringMongodbApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext container = SpringApplication.run(SpringMongodbApplication.class, args);

		CustomerService service = container.getBean(CustomerService.class);
//		CustomerDTO dto = new CustomerDTO();
//		dto.setName("abhishek");
//		dto.setCity("benguluru");
//		dto.setCusNum(45);
//		String id = IDGenerator.generateId();
//		dto.setId(id);
//		String status = service.registerCustomerInfo(dto);
//		System.out.println(status);

		service.findAll().forEach(System.out::println);

		System.out.println(service.removeDocument("6aca185a4d7a2cf4f2253da6"));
		service.findAll().forEach(System.out::println);

	}

}
