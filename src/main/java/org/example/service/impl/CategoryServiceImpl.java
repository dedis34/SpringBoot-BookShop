package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.category.CategoryRequestDto;
import org.example.dto.category.CategoryResponseDto;
import org.example.exception.CategoryNotFoundException;
import org.example.mapper.BookMapper;
import org.example.mapper.CategoryMapper;
import org.example.model.Category;
import org.example.repository.book.BookRepository;
import org.example.repository.category.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.example.model.Book;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category with id: " + id + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category with id: " + id + " not found"));

        category.setName(categoryRequestDto.name());
        category.setDescription(categoryRequestDto.description());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category with id: " + id + " not found"));
        categoryRepository.delete(category);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId, Pageable pageable) {
        Page<Book> booksPage = bookRepository.findByCategoriesId(categoryId, pageable);

        return booksPage.getContent().stream()
                .map(bookMapper::toDtoWithoutCategories)
                .collect(Collectors.toList());
    }
}
