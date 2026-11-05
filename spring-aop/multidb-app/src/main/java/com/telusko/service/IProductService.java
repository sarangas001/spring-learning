package com.telusko.service;


import com.telusko.entity.pd.Product;

import java.util.List;

public interface IProductService {
    public Product addProduct(Product pd);
    public List<Product> getAllProducts();
}
