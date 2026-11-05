package com.telusko.service;

import com.telusko.entity.cx.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICustomerService {
    public Customer addCustomer(Customer cx);
    public List<Customer> getAllCustomer();
}
