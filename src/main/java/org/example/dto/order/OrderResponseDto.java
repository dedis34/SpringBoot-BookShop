package org.example.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.example.dto.orderItem.OrderItemResponseDto;

public record OrderResponseDto(
        Long id,
        Long userId,
        Set<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        String status
) {
}
