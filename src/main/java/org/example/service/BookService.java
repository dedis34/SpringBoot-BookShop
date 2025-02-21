package org.example.service;

import org.example.dto.BookDto;
import org.example.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);
    List<BookDto> findAll();
    BookDto findById(Long id);
    void deleteById(Long id);
    void updateBookById(Long id, BookDto bookDto);
}
