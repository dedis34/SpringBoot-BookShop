package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.book.BookDto;
import org.example.dto.book.BookSearchParametersDto;
import org.example.dto.book.CreateBookRequestDto;
import org.example.service.BookService;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Create a new Book", description = "Create a new Book")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        return bookService.save(createBookRequestDto);
    }

    @Operation(summary = "Delete book by ID", description = "Delete a book by its ID")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @Operation(summary = "Update book by ID",
            description = "Update the details of an existing book by its ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateBookById(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        bookService.updateBookById(id, bookDto);
    }

    @Operation(summary = "Get all books", description = "Get a list of all books")
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BookDto> getAll(@Parameter(hidden = true) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Get book by ID", description = "Get a book by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(summary = "Search books", description = "Search books by various parameters with pagination")
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BookDto> searchBooks(@RequestBody BookSearchParametersDto searchParameters, Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
