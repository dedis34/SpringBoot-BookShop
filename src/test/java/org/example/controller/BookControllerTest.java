package org.example.controller;

import org.example.dto.book.BookDto;
import org.example.dto.book.CreateBookRequestDto;
import org.example.dto.book.BookSearchParametersDto;
import org.example.service.BookService;
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

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private BookDto bookDto;
    private CreateBookRequestDto createBookRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setIsbn("1234567890");
        bookDto.setPrice(BigDecimal.valueOf(19.99));
        bookDto.setDescription("Test Description");
        bookDto.setCoverImage("test.jpg");

        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("Test Book");
        createBookRequestDto.setAuthor("Test Author");
        createBookRequestDto.setIsbn("1234567890");
        createBookRequestDto.setPrice(BigDecimal.valueOf(19.99));
        createBookRequestDto.setDescription("Test Description");
        createBookRequestDto.setCoverImage("test.jpg");
    }

    @Test
    void createBook_WithValidRequest_ShouldReturnCreatedBook() {
        when(bookService.save(any(CreateBookRequestDto.class))).thenReturn(bookDto);

        BookDto result = bookController.createBook(createBookRequestDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals(HttpStatus.CREATED.value(), 201);
    }

    @Test
    void deleteBookById_WithExistingId_ShouldCallServiceMethod() {
        doNothing().when(bookService).deleteById(1L);

        bookController.deleteBookById(1L);

        verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    void updateBookById_WithValidRequest_ShouldCallServiceMethod() {
        doNothing().when(bookService).updateBookById(anyLong(), any(BookDto.class));

        bookController.updateBookById(1L, bookDto);

        verify(bookService, times(1)).updateBookById(1L, bookDto);
    }

    @Test
    void getAll_WhenCalled_ShouldReturnListOfBooks() {
        when(bookService.findAll(any(Pageable.class))).thenReturn(Collections.singletonList(bookDto));

        List<BookDto> result = bookController.getAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());
    }

    @Test
    void getBookById_WithExistingId_ShouldReturnBook() {
        when(bookService.findById(1L)).thenReturn(bookDto);

        BookDto result = bookController.getBookById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void searchBooks_WithValidParameters_ShouldReturnListOfBooks() {
        BookSearchParametersDto searchParameters = new BookSearchParametersDto();
        when(bookService.search(any(BookSearchParametersDto.class),
                any(Pageable.class))).thenReturn(Collections.singletonList(bookDto));

        List<BookDto> result = bookController.searchBooks(searchParameters, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());
    }
}
