package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.cartItem.AddItemToCartRequestDto;
import org.example.dto.cartItem.UpdateCartItemRequestDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.model.User;
import org.example.service.impl.ShoppingCartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "ShoppingCart management", description = "Endpoints for managing ShoppingCart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Operation(summary = "Add a new Book to ShoppingCart",
            description = "Adding a new Book to ShoppingCart")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto addToCart(@AuthenticationPrincipal User user,
                                             @RequestBody AddItemToCartRequestDto request) {
        return shoppingCartServiceImpl.addToCart(user.getId(), request);
    }

    @Operation(summary = "Remove Book from ShoppingCart",
            description = "Removing Book from ShoppingCart")
    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable Long cartItemId) {
        shoppingCartServiceImpl.removeCartItem(cartItemId);
    }

    @Operation(summary = "Update item quantity",
            description = "Updating item quantity in ShoppingCart")
    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCartResponseDto updateCartItem(@PathVariable Long cartItemId,
                                                  @Valid @RequestBody UpdateCartItemRequestDto request) {
        return shoppingCartServiceImpl.updateCartItem(cartItemId, request);
    }

    @Operation(summary = "Get a list of items from ShoppingCart",
            description = "Getting a list of items from ShoppingCart")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCartResponseDto getCart(@AuthenticationPrincipal User user) {
        return shoppingCartServiceImpl.getCart(user.getId());
    }
}
