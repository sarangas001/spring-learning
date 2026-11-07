package com.telusko.controller;

import com.telusko.model.Product;
import com.telusko.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173")
@Tag(name= "Product Controller", description = "Product Controller API")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product, @RequestPart MultipartFile image)
    {
        return new ResponseEntity<Product>(service.addProducts(product, image), HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id)
    {
        Product product = service.searchProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<String> updateProduct(@RequestPart Product product, @RequestPart MultipartFile image)
    {
        return new ResponseEntity<String>(service.updateProduct(product, image), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id)
    {
        String status = service.deleteProductById(id);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword)
    {
        List<Product> list = service.searchProductsByKeyword(keyword);
        System.out.println("Searching with " + keyword);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
