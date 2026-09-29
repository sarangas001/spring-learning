package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {


    @Autowired
    private IProductRepo repo;

    @Override
    public List<Product> searchByPrice(Double price) {
        return repo.findByPrice(price);
    }

    @Override
    public List<Product> searchByPriceGreaterThan(Double price) {
        return repo.findByPriceGreaterThan(price);
    }

    @Override
    public List<Product> searchByPriceLessThan(Double price) {
        return repo.findByPriceLessThan(price);
    }

    @Override
    public List<Product> searchByPriceBetween(Double minPrice, Double maxPrice) {
        return repo.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> searchByCategoryEquals(String category) {
        return repo.findByCategoryEquals(category);
    }

    @Override
    public List<Product> searchByCategoryInAndPriceBetween(List<String> categories, Double minPirce, Double maxPrice) {
        return repo.findByCategoryInAndPriceBetween(categories, minPirce, maxPrice);
    }

}
