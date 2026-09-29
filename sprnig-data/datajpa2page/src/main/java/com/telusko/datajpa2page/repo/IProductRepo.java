package com.telusko.datajpa2page.repo;

import com.telusko.datajpa2page.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends PagingAndSortingRepository<Product, Integer>
{

}
