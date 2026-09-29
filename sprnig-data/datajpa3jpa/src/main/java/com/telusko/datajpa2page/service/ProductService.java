package com.telusko.datajpa2page.service;

import com.telusko.datajpa2page.entity.Product;
import com.telusko.datajpa2page.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {


    @Autowired
    private IProductRepo repo;

    @Override
    public Product searchProductById(Integer id) {
        return repo.getReferenceById(id);
    }

    @Override
    public List<Product> searchProductsByIds(Iterable<Integer> ids) {
        return repo.findAllById(ids);
    }

    @Override
    public List<Product> searchProductsByProduct(Product product) {
        Example<Product> example = Example.of(product);
        return repo.findAll(example);
    }

    @Override
    public String deleteProductsByIdsCrud(List<Integer> ids) {
        List<Product> products = (List<Product>) repo.findAllById(ids);
        if(products.size()!=0) {
            repo.deleteAllById(ids);
            return "All Products records with given ids deleted";
        }else {
            return "Product info with given ids are not in records to delete";
        }
    }

    @Override
    public String deleteProductsByIds(List<Integer> ids) {
        List<Product> products = (List<Product>) repo.findAllById(ids);
        if(products.size()!=0) {
            repo.deleteAllByIdInBatch(ids);
            return "All Products records with given ids deleted";
        }else {
            return "Product info with given ids are not in records to delete";
        }
    }
}
