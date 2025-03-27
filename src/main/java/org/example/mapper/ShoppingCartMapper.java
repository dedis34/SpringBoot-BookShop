package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.cartItem.CartItemResponseDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.model.CartItem;
import org.example.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", expression = "java(mapCartItems(shoppingCart.getCartItems()))")
    ShoppingCartResponseDto toResponseDto(ShoppingCart shoppingCart);

    default Set<CartItemResponseDto> mapCartItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> new CartItemResponseDto(
                        cartItem.getId(),
                        cartItem.getBook().getId(),
                        cartItem.getBook().getTitle(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toSet());
    }
}
