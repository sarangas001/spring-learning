package com.telusko.spring_jdbc_2.repo;

import com.telusko.spring_jdbc_2.Entity.Employee;

import java.util.List;

public interface IEmployeeRepo {
    List<Employee> getEmployeeInfo();
}
