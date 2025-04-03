package org.example.dto.cartItem;

import lombok.Builder;

@Builder
public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity) {
}
