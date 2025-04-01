package org.example.dto.orderItem;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        int quantity,
        BigDecimal price
) {
}
