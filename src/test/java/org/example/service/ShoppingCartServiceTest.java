package org.example.service;

import org.example.dto.cartItem.AddItemToCartRequestDto;
import org.example.dto.cartItem.CartItemResponseDto;
import org.example.dto.cartItem.UpdateCartItemRequestDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.exception.BookNotFoundException;
import org.example.exception.CartItemNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.mapper.ShoppingCartMapper;
import org.example.model.Book;
import org.example.model.CartItem;
import org.example.model.ShoppingCart;
import org.example.model.User;
import org.example.repository.book.BookRepository;
import org.example.repository.cartItem.CartItemRepository;
import org.example.repository.shoppingCart.ShoppingCartRepository;
import org.example.repository.user.UserRepository;
import org.example.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    private User user;
    private ShoppingCart shoppingCart;
    private Book book;
    private CartItem cartItem;
    private ShoppingCartResponseDto shoppingCartResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(new HashSet<>());

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setBook(book);
        cartItem.setQuantity(2);
        cartItem.setShoppingCart(shoppingCart);

        CartItemResponseDto itemDto = CartItemResponseDto.builder()
                .id(1L)
                .bookId(1L)
                .bookTitle("Test Book")
                .quantity(2)
                .build();

        Set<CartItemResponseDto> cartItems = Set.of(itemDto);

        shoppingCartResponseDto = ShoppingCartResponseDto.builder()
                .id(1L)
                .userId(1L)
                .cartItems(cartItems)
                .build();
    }

    @Test
    void addToCart_WhenUserExistsAndBookExists_ShouldAddItemToCart() {
        AddItemToCartRequestDto request = new AddItemToCartRequestDto(1L, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(shoppingCartMapper.toResponseDto(any(ShoppingCart.class))).thenReturn(shoppingCartResponseDto);

        ShoppingCartResponseDto result = shoppingCartService.addToCart(1L, request);

        assertNotNull(result);
        assertEquals(1L, result.userId());
        assertEquals(shoppingCart.getId(), result.id());
        assertNotNull(result.cartItems());
        assertEquals(1, result.cartItems().size());

        CartItemResponseDto cartItemResponseDto = result.cartItems().iterator().next();
        assertEquals(1L, cartItemResponseDto.bookId());
        assertEquals("Test Book", cartItemResponseDto.bookTitle());
        assertEquals(2, cartItemResponseDto.quantity());

        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void addToCart_WhenUserNotFound_ShouldThrowException() {
        AddItemToCartRequestDto request = new AddItemToCartRequestDto(1L, 2);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> shoppingCartService.addToCart(1L, request));
    }

    @Test
    void getCart_WhenUserExists_ShouldReturnShoppingCart() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toResponseDto(shoppingCart)).thenReturn(shoppingCartResponseDto);

        ShoppingCartResponseDto result = shoppingCartService.getCart(1L);

        assertNotNull(result);
        assertEquals(1L, result.userId());
    }

    @Test
    void getCart_WhenUserNotFound_ShouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> shoppingCartService.getCart(1L));
    }

    @Test
    void updateCartItem_WhenItemExists_ShouldUpdateQuantity() {
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto(5);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));
        when(shoppingCartMapper.toResponseDto(any(ShoppingCart.class))).thenReturn(shoppingCartResponseDto);

        ShoppingCartResponseDto result = shoppingCartService.updateCartItem(1L, request);

        assertNotNull(result);
        assertEquals(5, cartItem.getQuantity());
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void updateCartItem_WhenItemNotFound_ShouldThrowException() {
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto(5);
        when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> shoppingCartService.updateCartItem(1L, request));
    }

    @Test
    void removeCartItem_WhenItemExists_ShouldRemoveItem() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));
        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));

        shoppingCartService.removeCartItem(1L);

        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void removeCartItem_WhenItemNotFound_ShouldThrowException() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> shoppingCartService.removeCartItem(1L));
    }

    @Test
    void addToCart_WhenBookNotFound_ShouldThrowException() {
        AddItemToCartRequestDto request = new AddItemToCartRequestDto(1L, 2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shoppingCartRepository.findByUser(user)).thenReturn(Optional.of(shoppingCart));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> shoppingCartService.addToCart(1L, request));
    }

}
