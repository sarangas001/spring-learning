package com.telusko.dto;

public record OrderItemRequest(
        Integer productId,
        Integer quantity

) {
}
