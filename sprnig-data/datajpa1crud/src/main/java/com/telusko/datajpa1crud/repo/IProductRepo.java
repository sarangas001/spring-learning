package com.telusko.datajpa1crud.repo;

import com.telusko.datajpa1crud.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends CrudRepository<Product, Integer>
{

}
