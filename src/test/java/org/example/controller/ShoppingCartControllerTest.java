package org.example.controller;

import org.example.dto.cartItem.CartItemResponseDto;
import org.example.dto.cartItem.UpdateCartItemRequestDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.model.User;
import org.example.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    private ShoppingCartResponseDto shoppingCartResponseDto;
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);

        Set<CartItemResponseDto> cartItems = new HashSet<>();
        cartItems.add(new CartItemResponseDto(1L, 1L, "Sample Book", 2));
        shoppingCartResponseDto = new ShoppingCartResponseDto(1L, 1L, cartItems);

        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateCartItem_WhenCartItemExists_ShouldUpdateQuantity() throws Exception {
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto(3);
        when(shoppingCartServiceImpl.updateCartItem(eq(1L), any(UpdateCartItemRequestDto.class)))
                .thenReturn(shoppingCartResponseDto);

        mockMvc.perform(put("/api/cart/cart-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(shoppingCartServiceImpl).updateCartItem(eq(1L), any(UpdateCartItemRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void removeCartItem_WhenCartItemExists_ShouldRemoveItem() throws Exception {
        mockMvc.perform(delete("/api/cart/cart-items/1"))
                .andExpect(status().isNoContent());

        verify(shoppingCartServiceImpl).removeCartItem(1L);
    }
}
