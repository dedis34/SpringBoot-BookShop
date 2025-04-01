package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.order.OrderRequestDto;
import org.example.dto.order.OrderResponseDto;
import org.example.dto.order.UpdateOrderStatusRequestDto;
import org.example.dto.orderItem.OrderItemResponseDto;
import org.example.model.User;
import org.example.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Tag(name = "Orders management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    @Operation(summary = "Place a new Order", description = "Create a new order for the authenticated user")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto placeOrder(@AuthenticationPrincipal User user, @RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderServiceImpl.placeOrder(user.getId(), orderRequestDto);
    }

    @Operation(summary = "Get user's order history", description = "Retrieve the order history for the authenticated user")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<OrderResponseDto> getOrderHistory(@AuthenticationPrincipal User user) {
        return orderServiceImpl.getOrderHistory(user.getId());
    }

    @Operation(summary = "Update order status", description = "Admin updates the status of an order")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@PathVariable Long id, @RequestBody @Valid UpdateOrderStatusRequestDto statusUpdateRequestDto) {
        orderServiceImpl.updateOrderStatus(id, statusUpdateRequestDto.status());
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<OrderItemResponseDto> getOrderItems(@AuthenticationPrincipal User user, @PathVariable Long orderId) {
        return orderServiceImpl.getOrderItems(user.getId(), orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public OrderItemResponseDto getOrderItem(@AuthenticationPrincipal User user, @PathVariable Long orderId, @PathVariable Long itemId) {
        return orderServiceImpl.getOrderItem(user.getId(), orderId, itemId);
    }
}


