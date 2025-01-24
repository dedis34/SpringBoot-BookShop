package org.example.repository;

import jakarta.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void updateStatus(Long orderId, String status);
}
