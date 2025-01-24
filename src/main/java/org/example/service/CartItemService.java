package org.example.service;

import org.example.model.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemService {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(Long id);
    List<CartItem> findAll();
    void deleteById(Long id);
}
