package com.telusko.service;

import com.telusko.dto.OrderItemRequest;
import com.telusko.dto.OrderItemResponse;
import com.telusko.dto.OrderRequest;
import com.telusko.dto.OrderResponse;
import com.telusko.exception.ProductNotFoundException;
import com.telusko.model.Order;
import com.telusko.model.OrderItem;
import com.telusko.model.Product;
import com.telusko.repo.IOrderRepo;
import com.telusko.repo.IProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderService{

    private static final String ORDER_STATUS = "PLACED";

    private IProductRepo productRepo;
    private IOrderRepo orderRepo;

    //Constructor Injection
    public OrderService(IProductRepo productRepo, IOrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    //Place Order
    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {

        Order order = new Order();

        order.setOrderId("ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus(ORDER_STATUS);
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();

        //Process each order item
        for (OrderItemRequest item : request.items())
        {
            Product product = productRepo.findById(item.productId())
                    .orElseThrow(() ->
                            new ProductNotFoundException("Product not found: " + item.productId()));

            if (product.getStockQuantity() < item.quantity()) throw new IllegalStateException("Insufficient stock for " + product.getName());

            // JPA Dirty Checking updates the managed entity
            product.setStockQuantity(
                    product.getStockQuantity() - item.quantity()
            );
            productRepo.save(product);

            OrderItem orderItem = new OrderItem(
                    product,
                    item.quantity(),
                    product.getPrice().multiply(BigDecimal.valueOf(item.quantity())),
                    order
            );

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        // Save order and cascade its items
        Order savedOrder = orderRepo.save(order);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    @Transactional
    public List<OrderResponse> getAllOrderResponse() {

        return orderRepo.findAll()
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    // Map Order Entity to OrderResponse DTO
    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> list = order.getOrderItems()
                .stream()
                .map(this::mapToOrderItemResponse)
                .toList();
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerName(),
                order.getEmail(),
                order.getStatus(),
                order.getOrderDate(),
                list
        );
    }

    // Map OrderItem Entity to OrderItemResponse DTO
    private OrderItemResponse mapToOrderItemResponse(OrderItem item)
    {
        return new OrderItemResponse(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }

}
