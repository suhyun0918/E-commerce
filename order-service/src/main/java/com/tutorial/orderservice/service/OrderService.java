package com.tutorial.orderservice.service;

import com.tutorial.orderservice.dto.OrderDto;
import com.tutorial.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrdersByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}

