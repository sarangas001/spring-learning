package com.telusko.springMongodb.service;

import com.telusko.springMongodb.dto.CustomerDTO;
import com.telusko.springMongodb.model.Customer;

import java.util.List;

public interface ICustomerService
{
    String registerCustomerInfo(CustomerDTO dto);
    List<Customer> findAll();
    String removeDocument(String id);
}
