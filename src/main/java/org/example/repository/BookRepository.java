package org.example.repository;

import java.awt.print.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
}
