package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.order.OrderResponseDto;
import org.example.dto.orderItem.OrderItemResponseDto;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapOrderItems")
    OrderResponseDto toResponseDto(Order order);

    @Named("mapStatus")
    default String mapStatus(Order.Status status) {
        return status != null ? status.name() : null;
    }

    @Named("mapOrderItems")
    default Set<OrderItemResponseDto> mapOrderItems(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> new OrderItemResponseDto(
                        orderItem.getId(),
                        orderItem.getBook().getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice()
                ))
                .collect(Collectors.toSet());
    }
}
