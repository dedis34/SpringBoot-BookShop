package org.example.service;

import jakarta.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
}
