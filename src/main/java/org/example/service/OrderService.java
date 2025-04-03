package org.example.service;

import org.example.dto.order.OrderRequestDto;
import org.example.dto.order.OrderResponseDto;
import org.example.dto.orderItem.OrderItemResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto);
    Page<OrderResponseDto> getOrderHistory(Long userId, Pageable pageable);
    void updateOrderStatus(Long orderId, String newStatus);
    Page<OrderItemResponseDto> getOrderItems(Long userId, Long orderId, Pageable pageable);
    OrderItemResponseDto getOrderItem(Long userId, Long orderId, Long itemId);
}
