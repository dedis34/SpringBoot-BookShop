package org.example.dto.cartItem;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity) {
}
