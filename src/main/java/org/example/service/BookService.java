package org.example.service;

import org.example.dto.BookDto;
import org.example.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto create(CreateBookRequestDto createBookRequestDto);
    List<BookDto> getAll();
    BookDto getById(Long id);
}
