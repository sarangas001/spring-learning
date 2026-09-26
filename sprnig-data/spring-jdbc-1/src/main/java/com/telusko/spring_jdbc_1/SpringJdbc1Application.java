package com.telusko.spring_jdbc_1;

import com.telusko.spring_jdbc_1.entity.Employee;
import com.telusko.spring_jdbc_1.repo.IEmployeeRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class SpringJdbc1Application
{

	public static void main(String[] args)
	{
		ConfigurableApplicationContext container = SpringApplication.run(SpringJdbc1Application.class, args);

		IEmployeeRepo repo = container.getBean(IEmployeeRepo.class);

		repo.getEmployeeInfo().forEach(e-> System.out.println(e));

//		List<Employee> list = repo.getEmployeeInfo();


//		Iterator<Employee> itr = list.iterator();
//
//		while (itr.hasNext()) {
//			System.out.println(itr.next());
//		}
	}

}
