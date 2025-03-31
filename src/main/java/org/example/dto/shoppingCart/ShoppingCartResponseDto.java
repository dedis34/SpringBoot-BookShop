package org.example.dto.shoppingCart;

import org.example.dto.cartItem.CartItemResponseDto;
import java.util.Set;

public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        Set<CartItemResponseDto> cartItems) {
}
