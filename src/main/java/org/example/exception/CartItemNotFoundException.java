package org.example.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException() {
        super();
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }

    public CartItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
