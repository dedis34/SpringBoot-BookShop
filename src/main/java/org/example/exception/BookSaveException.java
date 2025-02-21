package org.example.exception;

public class BookSaveException extends RuntimeException {
    public BookSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
