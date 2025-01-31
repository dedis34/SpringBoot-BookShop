package org.example.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Exception e) {
        super(message);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}
