package com.tutorial.orderservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String OrderId);

    Iterable<OrderEntity> findByUserId(String userId);
}
