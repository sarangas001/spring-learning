package com.telusko.service;

import com.telusko.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {
    void registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer id);
    List<Customer> searchCustomerByNameAndEmail(String name, String email);
    Customer searchCustomerById(Integer id);
}
