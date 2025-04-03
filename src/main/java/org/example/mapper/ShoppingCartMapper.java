package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.cartItem.CartItemResponseDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.model.CartItem;
import org.example.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", qualifiedByName = "mapCartItems")
    ShoppingCartResponseDto toResponseDto(ShoppingCart shoppingCart);

    @Named("mapCartItems")
    default Set<CartItemResponseDto> mapCartItems(Set<CartItem> cartItems) {
        if (cartItems == null) {
            return Set.of();
        }
        return cartItems.stream()
                .map(this::mapCartItem)
                .collect(Collectors.toSet());
    }

    default CartItemResponseDto mapCartItem(CartItem cartItem) {
        if (cartItem == null || cartItem.getBook() == null) {
            return null;
        }
        return CartItemResponseDto.builder()
                .id(cartItem.getId())
                .bookId(cartItem.getBook().getId())
                .bookTitle(cartItem.getBook().getTitle())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
