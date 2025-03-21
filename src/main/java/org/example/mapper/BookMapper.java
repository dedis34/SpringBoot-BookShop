package org.example.mapper;

import org.example.config.MapperConfig;
import org.example.dto.book.BookDto;
import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.book.CreateBookRequestDto;
import org.example.model.Book;
import org.example.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);
    Book toModel(CreateBookRequestDto createBookRequestDto);
    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }
}
