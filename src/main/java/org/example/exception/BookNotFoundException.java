package org.example.exception;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(String message) {
    super(message);
  }

  public BookNotFoundException() {
    super("Can't find Book entity");
  }
}
