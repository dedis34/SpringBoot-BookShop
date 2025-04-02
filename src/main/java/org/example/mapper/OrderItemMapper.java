package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.orderItem.OrderItemResponseDto;
import org.example.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
