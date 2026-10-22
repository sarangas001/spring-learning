package com.telusko.service;

import com.telusko.entity.Customer;
import com.telusko.repo.ICustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{

    private ICustomerRepo repo;

    @Autowired
    public void setRepo(ICustomerRepo repo) {
        this.repo = repo;
    }

    @Override
    public void registerCustomer(Customer customer) {
        repo.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {
        repo.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Customer> searchCustomerByNameAndEmail(String name, String email) {
        return repo.findByNameAndEmail(name, email);
    }

    @Override
    public Customer searchCustomerById(Integer id) {
        return repo.findById(id).get();
    }
}
