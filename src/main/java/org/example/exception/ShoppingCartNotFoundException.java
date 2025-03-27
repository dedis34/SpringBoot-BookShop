package org.example.exception;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException() {
        super();
    }

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }

    public ShoppingCartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartNotFoundException(Throwable cause) {
        super(cause);
    }
}
