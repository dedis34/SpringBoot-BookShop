package org.example.service;

import org.example.model.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(Long id);
    List<OrderItem> findAll();
    void deleteById(Long id);
}
