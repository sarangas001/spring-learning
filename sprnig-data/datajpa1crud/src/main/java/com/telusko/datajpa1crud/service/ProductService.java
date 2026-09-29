package com.telusko.datajpa1crud.service;

import com.telusko.datajpa1crud.entity.Product;
import com.telusko.datajpa1crud.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    public String saveproduct(Product product) {
        Product pd = repo.save(product);
        return "Product stored with id " + pd.getId();
    }

    @Override
    public Iterable<Product> saveMultipleProducts(Iterable<Product> products) {
        return repo.saveAll(products);
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Iterable<Product> getAllProductsByIds(List<Integer> ids) {
        return repo.findAllById(ids);
    }

    @Override
    public Product getProductById(Integer id) {
        Optional<Product> optional = repo.findById(id);
        if(optional.isPresent())
        {
            Product product = optional.get();
            return product;
        }
        else
            return new Product();

    }

    @Override
    public Boolean isProductAvailable(Integer id) {
        return repo.existsById(id);

    }

    @Override
    public Long getTotalProductsCount() {
        return repo.count();
    }

    @Override
    public String deleteProductById(Integer id) {
        Optional<Product> optional = repo.findById(id);
        if (optional.isPresent()) {
            repo.deleteById(id);
            return "Product id with " + id + " Successfully deleted";
        }else {
            return "Product not deleted";
        }

    }

    @Override
    public String deleteProdcutsByIds(Iterable<Integer> ids) {
        List<Product> products = (List<Product>) repo.findAllById(ids);
        //option 1
        if(products.size() != 0) {
            repo.deleteAllById(ids);
            return "Products are deleted";
        }else {
            return "Products are not in the record";
        }

        // option 2
//        if (products.size() == products.size()) {
//            repo.deleteAllById(ids);
//            return "Products are deleted";
//        }else {
//            return "Products are not in the record";
//        }

    }


}
