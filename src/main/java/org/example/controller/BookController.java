package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.dto.BookSearchParametersDto;
import org.example.dto.CreateBookRequestDto;
import org.example.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        return bookService.save(createBookRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateBookById(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookService.updateBookById(id, bookDto);
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

}
