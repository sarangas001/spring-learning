package com.telusko.service;

import com.telusko.dto.OrderItemResponse;
import com.telusko.dto.OrderRequest;
import com.telusko.dto.OrderResponse;
import com.telusko.exception.ProductNotFoundException;
import com.telusko.model.Order;
import com.telusko.model.OrderItem;
import com.telusko.model.Product;
import com.telusko.repo.IOrderRepo;
import com.telusko.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private IOrderRepo orderRepo;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        Order order = new Order();

        order.setOrderId("ORD" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();

        request.items().forEach(item -> {
                    Product product = productRepo.findById(item.productId())
                            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
                    // Update stock
                    product.setStockQuantity(product.getStockQuantity() - item.quantity());

                    productRepo.save(product);

                    //Calculate total price
                    OrderItem orderItem = new OrderItem(product,
                            item.quantity(),
                            product.getPrice().multiply(BigDecimal.valueOf(item.quantity())),
                            order);

                    orderItems.add(orderItem);

                }

                );

        order.setOrderItems(orderItems);

        // Save order and its items
        Order saved = orderRepo.save(order);

        ArrayList<OrderItemResponse> itemResponses = new ArrayList<>();

        order.getOrderItems().forEach(item -> {
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()

            );

            itemResponses.add(orderItemResponse);
        });

        OrderResponse orderResponse = new OrderResponse(
                saved.getOrderId(),
                saved.getCustomerName(),
                saved.getEmail(),
                saved.getStatus(),
                saved.getOrderDate(),
                itemResponses
        );

        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrderResponse() {

        List<Order> list = orderRepo.findAll();
        ArrayList<OrderResponse> orderResponses = new ArrayList<>();
        list.forEach(order -> {

            List<OrderItemResponse> orderItemResponseList =
                    new ArrayList<>();

            order.getOrderItems().forEach(item -> {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getTotalPrice()
                        );
                orderItemResponseList.add(orderItemResponse);
            });

            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId() , order.getCustomerName(),
                    order.getEmail(), order.getStatus(),
                    order.getOrderDate(),
                    orderItemResponseList
            );
            orderResponses.add(orderResponse);
        });

        return orderResponses;
    }
}
