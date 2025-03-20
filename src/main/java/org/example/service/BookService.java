package org.example.service;

import org.example.dto.book.BookDto;
import org.example.dto.book.BookSearchParametersDto;
import org.example.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);
    List<BookDto> findAll(Pageable pageable);
    BookDto findById(Long id);
    void deleteById(Long id);
    void updateBookById(Long id, BookDto bookDto);
    List<BookDto> search(BookSearchParametersDto params, Pageable pageable);
}
