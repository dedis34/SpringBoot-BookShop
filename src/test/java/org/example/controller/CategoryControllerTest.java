package org.example.controller;

import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.category.CategoryRequestDto;
import org.example.dto.category.CategoryResponseDto;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryRequestDto = new CategoryRequestDto("Test Category", "Test Description");
        categoryResponseDto = new CategoryResponseDto(1L, "Test Category", "Test Description");
    }

    @Test
    void createCategory_WithValidRequest_ShouldReturnCreatedCategory() {
        when(categoryService.save(any(CategoryRequestDto.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryController.createCategory(categoryRequestDto);

        assertNotNull(result);
        assertEquals("Test Category", result.name());
        assertEquals(HttpStatus.CREATED.value(), 201);
    }

    @Test
    void deleteCategory_WithExistingId_ShouldCallServiceMethod() {
        doNothing().when(categoryService).deleteById(1L);

        categoryController.deleteCategory(1L);

        verify(categoryService, times(1)).deleteById(1L);
    }

    @Test
    void updateCategory_WithValidRequest_ShouldReturnUpdatedCategory() {
        when(categoryService.update(any(Long.class), any(CategoryRequestDto.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryController.updateCategory(1L, categoryRequestDto);

        assertNotNull(result);
        assertEquals("Test Category", result.name());
    }

    @Test
    void getAll_WhenCalled_ShouldReturnListOfCategories() {
        when(categoryService.findAll(any(Pageable.class))).thenReturn(Collections.singletonList(categoryResponseDto));

        List<CategoryResponseDto> result = categoryController.getAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Category", result.getFirst().name());
    }

    @Test
    void getCategoryById_WithExistingId_ShouldReturnCategory() {
        when(categoryService.getById(1L)).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryController.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Test Category", result.name());
    }

    @Test
    void getBooksByCategoryId_WithExistingId_ShouldReturnListOfBooks() {
        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds(1L, "Test Book",
                "Test Author", "1234567890", BigDecimal.valueOf(19.99),
                "Test Description", "test.jpg");
        when(categoryService.getBooksByCategoryId(1L,
                Pageable.unpaged())).thenReturn(Collections.singletonList(bookDto));

        List<BookDtoWithoutCategoryIds> result = categoryController.getBooksByCategoryId(1L, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
