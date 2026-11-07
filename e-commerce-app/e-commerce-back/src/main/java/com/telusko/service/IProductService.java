package com.telusko.service;

import com.telusko.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IProductService {
    List<Product> getAllProducts();
    Product addProducts(Product product, MultipartFile image);
    Product searchProductById(Integer id);
    String deleteProductById(Integer id);
    String updateProduct(Product product, MultipartFile image);
    List<Product> searchProductsByKeyword(String keyword);
}
