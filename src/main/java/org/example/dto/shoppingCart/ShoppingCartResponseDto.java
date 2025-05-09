package org.example.dto.shoppingCart;

import lombok.Builder;
import org.example.dto.cartItem.CartItemResponseDto;
import java.util.Set;

@Builder
public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        Set<CartItemResponseDto> cartItems) {
}
