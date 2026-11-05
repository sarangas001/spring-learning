package com.telusko.service;

import com.telusko.entity.pd.Product;
import com.telusko.repo.pd.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private IProductRepo repo;

    @Autowired
    public ProductService(IProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public Product addProduct(Product pd) {
        return repo.save(pd);
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
}
