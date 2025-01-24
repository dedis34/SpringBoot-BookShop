package org.example.service;

import java.awt.print.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
