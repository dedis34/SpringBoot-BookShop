package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.cartItem.AddItemToCartRequestDto;
import org.example.dto.cartItem.UpdateCartItemRequestDto;
import org.example.dto.shoppingCart.ShoppingCartResponseDto;
import org.example.exception.BookNotFoundException;
import org.example.exception.CartItemNotFoundException;
import org.example.exception.ShoppingCartNotFoundException;
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
import org.example.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Transactional
    public ShoppingCartResponseDto addToCart(Long userId, AddItemToCartRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    newCart.setCartItems(new HashSet<>());
                    return shoppingCartRepository.save(newCart);
                });

        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (request.quantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().equals(book))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setShoppingCart(shoppingCart);
                    newItem.setBook(book);
                    newItem.setQuantity(0);
                    shoppingCart.getCartItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toResponseDto(shoppingCart);
    }

    public ShoppingCartResponseDto getCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        return shoppingCartMapper.toResponseDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartResponseDto updateCartItem(Long cartItemId, UpdateCartItemRequestDto request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        if (request.quantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        cartItem.setQuantity(request.quantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toResponseDto(cartItem.getShoppingCart());
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        ShoppingCart shoppingCart = cartItem.getShoppingCart();
        shoppingCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        if (shoppingCart.getCartItems().isEmpty()) {
            shoppingCartRepository.delete(shoppingCart);
        }
    }
}
