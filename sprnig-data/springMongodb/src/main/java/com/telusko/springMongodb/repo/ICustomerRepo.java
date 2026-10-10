package com.telusko.springMongodb.repo;

import com.telusko.springMongodb.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepo extends MongoRepository<Customer, String> {
}
