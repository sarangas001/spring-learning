package com.telusko.repo;

import com.telusko.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Integer> {
    List<Customer> findByNameAndEmail(String name, String email);
}
