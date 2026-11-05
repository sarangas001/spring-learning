package com.telusko.service;

import com.telusko.entity.cx.Customer;
import com.telusko.repo.cx.ICustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private ICustomerRepo repo;

    @Autowired
    public CustomerService(ICustomerRepo repo)
    {
        this.repo = repo;
    }

    @Override
    public Customer addCustomer(Customer cx) {
        return repo.save(cx);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return repo.findAll();
    }
}
