package org.example.service;

import org.example.dto.cartItem.AddItemToCartRequestDto;
import org.example.dto.cartItem.UpdateCartItemRequestDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto addToCart(Long userId, AddItemToCartRequestDto request);
    ShoppingCartResponseDto getCart(Long userId);
    ShoppingCartResponseDto updateCartItem(Long cartItemId, UpdateCartItemRequestDto request);
    void removeCartItem(Long cartItemId);
}
