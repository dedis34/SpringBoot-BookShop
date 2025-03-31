package org.example.dto.cartItem;

public record AddItemToCartRequestDto(
        Long bookId,
        int quantity) {
}
