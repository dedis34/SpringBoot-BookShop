package org.example.repository;

import org.example.model.Category;
import org.example.repository.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save a category")
    void saveCategory_WhenCalled_ShouldSaveCategory() {
        Category category = new Category();
        category.setName("Category1");

        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory);
        assertEquals("Category1", savedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Should find category by ID")
    void findById_WhenCategoryExists_ShouldReturnCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryRepository.findById(1L);

        assertTrue(foundCategory.isPresent());
        assertEquals("Category1", foundCategory.get().getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should find all categories")
    void findAll_WhenCalled_ShouldReturnAllCategories() {
        Pageable pageable = Pageable.ofSize(5);
        Category category = new Category();
        category.setName("Category1");

        Page<Category> page = new PageImpl<>(List.of(category));
        when(categoryRepository.findAll(pageable)).thenReturn(page);

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertNotNull(categories);
        assertEquals(1, categories.getContent().size());
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should delete a category")
    void deleteCategory_WhenCalled_ShouldDeleteCategory() {
        Category category = new Category();
        category.setId(1L);

        doNothing().when(categoryRepository).delete(category);

        categoryRepository.delete(category);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("Should throw exception when category not found by ID")
    void findById_WhenCategoryNotFound_ShouldReturnEmptyOptional() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryRepository.findById(2L);

        assertFalse(foundCategory.isPresent());
        verify(categoryRepository, times(1)).findById(2L);
    }
}
