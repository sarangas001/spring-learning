package com.telusko.spring_jdbc_2;

import com.telusko.spring_jdbc_2.Entity.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.telusko.spring_jdbc_2.repo.EmployeeRepo2;

@SpringBootApplication
public class SpringJdbc2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext container = SpringApplication.run(SpringJdbc2Application.class, args);

		EmployeeRepo2 repo2 = container.getBean(EmployeeRepo2.class);

		Employee employee = new Employee();

		employee.setId(4);
		employee.setName("Aseka");
		employee.setCity("NuwaraEliya");

		repo2.input(employee);


	}

}
