package com.telusko.controller;

import com.telusko.entity.cx.Customer;
import com.telusko.entity.pd.Product;
import com.telusko.service.ICustomerService;
import com.telusko.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeluskoController {

    private ICustomerService cxService;
    private IProductService pdService;

    @Autowired
    public TeluskoController(ICustomerService cxService, IProductService pdService) {
        this.cxService = cxService;
        this.pdService = pdService;
    }

    @PostMapping("/addCx")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer cx)
    {
        Customer customer = cxService.addCustomer(cx);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @PostMapping("/addPd")
    public ResponseEntity<Product> addProduct(@RequestBody Product pd)
    {
        Product product = pdService.addProduct(pd);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCxs")
    public ResponseEntity<List<Customer>> getAllCxs()
    {
        List<Customer> list = cxService.getAllCustomer();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getAllPds")
    public ResponseEntity<List<Product>> getAllPds()
    {
        List<Product> list = pdService.getAllProducts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
