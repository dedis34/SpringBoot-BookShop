package org.example.service;

import java.util.List;
import org.example.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
