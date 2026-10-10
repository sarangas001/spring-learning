package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.view.View;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService
{
    public List<Product> findByCategory(String cat);
    List<String> findByPrice(Double minPrice, Double maxPrice);
    public int updateThePriceByProductName(Double newPrice, String productName);
    public int insertTheProductInfo(Integer id,String productName,String category, Double price, Integer quantity);
}
