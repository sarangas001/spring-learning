package com.telusko.service;

import com.telusko.dto.OrderRequest;
import com.telusko.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IOrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
    List<OrderResponse> getAllOrderResponse();
}
