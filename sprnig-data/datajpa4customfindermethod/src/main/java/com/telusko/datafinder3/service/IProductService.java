package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;

import java.util.List;

public interface IProductService
{
    List<Product> searchByPrice(Double price);
    List<Product> searchByPriceGreaterThan(Double price);
    List<Product> searchByPriceLessThan(Double price);
    List<Product> searchByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> searchByCategoryEquals(String category);
    List<Product> searchByCategoryInAndPriceBetween(List<String> categories, Double minPirce, Double maxPrice);
}
