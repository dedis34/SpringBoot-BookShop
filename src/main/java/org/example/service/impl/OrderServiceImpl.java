package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.order.OrderRequestDto;
import org.example.dto.order.OrderResponseDto;
import org.example.dto.orderItem.OrderItemResponseDto;
import org.example.exception.OrderItemNotFoundException;
import org.example.exception.OrderNotFoundException;
import org.example.exception.ShoppingCartNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.mapper.OrderMapper;
import org.example.mapper.OrderItemMapper;
import org.example.model.*;
import org.example.repository.shoppingCart.ShoppingCartRepository;
import org.example.repository.order.OrderRepository;
import org.example.repository.user.UserRepository;
import org.example.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new ShoppingCartNotFoundException("Shopping cart is empty");
        }

        Order order = prepareOrder(user, orderRequestDto, cartItems);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return orderMapper.toResponseDto(order);
    }

    private Order prepareOrder(User user, OrderRequestDto orderRequestDto, Set<CartItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderRequestDto.shippingAddress());

        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                }).collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        order.setTotal(orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrderHistory(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus(Order.Status.valueOf(newStatus));
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponseDto> getOrderItems(Long userId, Long orderId, Pageable pageable) {
        return orderRepository.findOrderItemsByOrderIdAndUserId(orderId, userId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponseDto getOrderItem(Long userId, Long orderId, Long itemId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found or does not belong to user"));

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found"));

        return orderItemMapper.toDto(orderItem);
    }
}
