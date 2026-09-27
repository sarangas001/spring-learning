package com.telusko.datajpa1crud.service;

import com.telusko.datajpa1crud.entity.Product;

import java.util.List;

public interface IProductService
{
    String saveproduct(Product product);
    Iterable<Product> saveMultipleProducts(Iterable<Product> products);
    Iterable<Product> getAllProducts();
    Iterable<Product> getAllProductsByIds(List<Integer> ids);
    Product getProductById(Integer id);
    Boolean isProductAvailable(Integer id);
    Long getTotalProductsCount();
}
