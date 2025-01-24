package org.example.repository;

import org.example.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository {
    ShoppingCart save(ShoppingCart shoppingCart);
    Optional<ShoppingCart> findById(Long id);
    List<ShoppingCart> findAll();
    void clearCart(Long id);
}
