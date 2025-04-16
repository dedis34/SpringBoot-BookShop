package org.example.service;

import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.category.CategoryRequestDto;
import org.example.dto.category.CategoryResponseDto;
import org.example.exception.CategoryNotFoundException;
import org.example.mapper.BookMapper;
import org.example.mapper.CategoryMapper;
import org.example.model.Book;
import org.example.model.Category;
import org.example.repository.book.BookRepository;
import org.example.repository.category.CategoryRepository;
import org.example.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find all categories")
    void findAll_WhenCalled_ShouldReturnAllCategories() {
        Pageable pageable = Pageable.ofSize(5);
        Category category = new Category();
        category.setName("Category1");

        Page<Category> page = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(new CategoryResponseDto(1L, "Category1",
                "Description"));

        List<CategoryResponseDto> result = categoryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should get category by ID")
    void getById_WhenCategoryExists_ShouldReturnCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category1");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(new CategoryResponseDto(1L, "Category1",
                "Description"));

        CategoryResponseDto result = categoryService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when category not found by ID")
    void getById_WhenCategoryNotFound_ShouldThrowException() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getById(2L));
    }

    @Test
    @DisplayName("Should save a new category")
    void save_WhenCalled_ShouldSaveCategory() {
        CategoryRequestDto requestDto = new CategoryRequestDto("New Category", "Description");
        Category category = new Category();
        category.setName("New Category");

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(new CategoryResponseDto(1L, "New Category",
                "Description"));

        CategoryResponseDto result = categoryService.save(requestDto);

        assertNotNull(result);
        assertEquals("New Category", result.name());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should update existing category")
    void update_WhenCategoryExists_ShouldUpdateCategory() {
        CategoryRequestDto requestDto = new CategoryRequestDto("Updated Category",
                "Updated Description");
        Category category = new Category();
        category.setId(1L);
        category.setName("Original Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(new CategoryResponseDto(1L, "Updated Category",
                "Updated Description"));

        CategoryResponseDto result = categoryService.update(1L, requestDto);

        assertNotNull(result);
        assertEquals("Updated Category", result.name());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing category")
    void update_WhenCategoryNotFound_ShouldThrowException() {
        CategoryRequestDto requestDto = new CategoryRequestDto("Updated Category",
                "Updated Description");
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(2L, requestDto));
        verify(categoryRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Should delete category by ID")
    void deleteById_WhenCategoryExists_ShouldDeleteCategory() {
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing category")
    void deleteById_WhenCategoryNotFound_ShouldThrowException() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteById(2L));
    }

    @Test
    @DisplayName("Should get books by category ID")
    void getBooksByCategoryId_WhenCategoryExists_ShouldReturnBooks() {
        Pageable pageable = Pageable.ofSize(5);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book1");
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findByCategoriesId(1L, pageable)).thenReturn(bookPage);
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(new BookDtoWithoutCategoryIds(
                1L, "Book1", "Author1", "123456", null, null, null));

        List<BookDtoWithoutCategoryIds> result = categoryService.getBooksByCategoryId(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Book1", result.getFirst().title());
        verify(bookRepository, times(1)).findByCategoriesId(1L, pageable);
    }
}
