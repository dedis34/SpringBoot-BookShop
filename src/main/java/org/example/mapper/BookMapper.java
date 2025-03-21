package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.book.BookDto;
import org.example.dto.book.CreateBookRequestDto;
import org.example.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);
    Book toModel(CreateBookRequestDto createBookRequestDto);
}
