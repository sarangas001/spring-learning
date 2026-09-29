package com.telusko.datajpa2page.service;

import com.telusko.datajpa2page.entity.Product;

import java.util.List;

public interface IProductService
{
    Product searchProductById(Integer id);
    List<Product> searchProductsByIds(Iterable<Integer> ids);
    List<Product> searchProductsByProduct(Product product);
    String deleteProductsByIdsCrud(List<Integer> ids);
    String deleteProductsByIds(List<Integer> ids);
}
