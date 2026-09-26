package com.telusko.spring_jdbc_1.repo;

import com.telusko.spring_jdbc_1.entity.Employee;

import java.util.List;

public interface IEmployeeRepo
{

    List<Employee> getEmployeeInfo();

}
