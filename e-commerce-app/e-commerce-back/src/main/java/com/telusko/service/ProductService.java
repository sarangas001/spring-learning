package com.telusko.service;

import com.telusko.dto.OrderItemRequest;
import com.telusko.exception.ImageProcessingException;
import com.telusko.exception.ProductNotFoundException;
import com.telusko.model.Product;
import com.telusko.repo.IProductRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepo repo;

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }


    @Override
    public Product addProducts(Product product, MultipartFile image) {
        try {
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setImageData(image.getBytes());
            Product save = repo.save(product);
            return save;
        } catch (IOException e) {
            throw new ImageProcessingException(e.getMessage());
        }

    }

    @Override
    public Product searchProductById(Integer id) {
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found id : " + id));
        return product;
    }

    @Override
    public String deleteProductById(Integer id) {
        repo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found id : " + id));
        repo.deleteById(id);
        return "Successfully, Deleted the product id : " + id;
    }

    @Override
    public String updateProduct(Product product, MultipartFile image) {
        try{
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setImageData(image.getBytes());
            repo.save(product);
            return "Successfully, Updated products";
        }catch (IOException ex)
        {
            throw new ImageProcessingException(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> searchProductsByKeyword(String keyword)
    {
        return repo.searchProductsByKeyword(keyword);
    }
}
