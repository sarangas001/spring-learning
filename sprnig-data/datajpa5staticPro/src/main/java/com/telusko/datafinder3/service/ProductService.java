package com.telusko.datafinder3.service;

import com.telusko.datafinder3.entity.Product;
import com.telusko.datafinder3.repo.IProductRepo;
import com.telusko.datafinder3.view.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {


    @Autowired
    private IProductRepo repo;


    @Override
    public List<ResultView> searchByPriceLessThan(Double price) {
        return repo.findByPriceLessThan(price);
    }
}
