package org.example.repository;

import org.example.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book create(Book book);
    List<Book> getAll();
    Optional<Book> getById(Long id);
}
