package com.telusko.datafinder3.service;

import com.telusko.datafinder3.repo.IProductRepo;
import com.telusko.datafinder3.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {


    @Autowired
    private IProductRepo repo;


    @Override
    public <T extends View> List<T> searchByPriceLessThan(Double price, Class<T> cls) {
        return repo.findByPriceLessThan(price, cls);
    }
}
