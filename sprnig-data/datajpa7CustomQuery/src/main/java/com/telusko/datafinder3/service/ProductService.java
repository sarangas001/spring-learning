package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.repo.IProductRepo;
import com.telusko.datafinder3.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Override
    public int insertTheProductInfo(Integer id, String productName, String category, Double price, Integer quantity) {
        return repo.insertProductInfo(id, productName, category, price, quantity);
    }

    @Autowired
    private IProductRepo repo;


    @Override
    public List<Product> findByCategory(String cat) {
        return repo.searchByCategory(cat);
    }

    @Override
    public List<String> findByPrice(Double minPrice, Double maxPrice) {
        return repo.searchByPrice(minPrice, maxPrice);
    }

    @Override
    public int updateThePriceByProductName(Double newPrice, String productName) {
        return repo.updatePriceByProductName(newPrice, productName);
    }
}
