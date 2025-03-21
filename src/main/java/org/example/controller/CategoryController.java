package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.book.BookDtoWithoutCategoryIds;
import org.example.dto.category.CategoryRequestDto;
import org.example.dto.category.CategoryResponseDto;
import org.example.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Tag(name = "Categories management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a new Category", description = "Creating a new Category")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @Operation(summary = "Delete category by ID", description = "Deleting category by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @Operation(summary = "Update category", description = "Updating category")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CategoryResponseDto updateCategory(@PathVariable Long id,
                                              @Valid @RequestBody
                                              CategoryRequestDto categoryRequestDto){
        return categoryService.update(id, categoryRequestDto);
    }

    @Operation(summary = "Get all categories", description = "Getting a list of all categories")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<CategoryResponseDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }


    @Operation(summary = "Get category by ID", description = "Getting category by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CategoryResponseDto getCategoryById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @Operation(summary = "Get a book by category's ID",
            description = "Getting a book by category's ID")
    @GetMapping("/{id}/books")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId
            (@PathVariable Long id, Pageable pageable){
        return categoryService.getBooksByCategoryId(id, pageable);
    }
}
