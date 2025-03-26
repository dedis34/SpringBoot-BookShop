package org.example.service;

import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.category.CategoryRequestDto;
import org.example.dto.category.CategoryResponseDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);
    CategoryResponseDto getById(Long id);
    CategoryResponseDto save(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto);
    void deleteById(Long id);
    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId, Pageable pageable);
}
