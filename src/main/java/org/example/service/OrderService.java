package org.example.service;

import org.example.dto.order.OrderRequestDto;
import org.example.dto.order.OrderResponseDto;
import org.example.dto.orderItem.OrderItemResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(Long userId, OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getOrderHistory(Long userId);
    void updateOrderStatus(Long orderId, String newStatus);
    List<OrderItemResponseDto> getOrderItems(Long userId, Long orderId);
    OrderItemResponseDto getOrderItem(Long userId, Long orderId, Long itemId);
}
