package com.telusko.controller;

import com.telusko.dto.OrderRequest;
import com.telusko.dto.OrderResponse;
import com.telusko.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private IOrderService service;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest)
    {
        OrderResponse request = service.placeOrder(orderRequest);
        return new ResponseEntity<OrderResponse>(request, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>> getAllOrders()
    {
        List<OrderResponse> responses = service.getAllOrderResponse();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
