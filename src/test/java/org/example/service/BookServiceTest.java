package org.example.service;

import org.example.dto.book.BookDto;
import org.example.dto.book.BookSearchParametersDto;
import org.example.dto.book.CreateBookRequestDto;
import org.example.exception.BookNotFoundException;
import org.example.exception.BookSaveException;
import org.example.exception.BookValidationException;
import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.repository.book.BookRepository;
import org.example.repository.book.BookSpecificationBuilder;
import org.example.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    private CreateBookRequestDto createBookRequestDto;
    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("Test Book");
        createBookRequestDto.setAuthor("Test Author");
        createBookRequestDto.setIsbn("1234567890");
        createBookRequestDto.setPrice(BigDecimal.valueOf(19.99));

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPrice(BigDecimal.valueOf(19.99));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setIsbn("1234567890");
        bookDto.setPrice(BigDecimal.valueOf(19.99));
    }

    @Test
    void save_WhenValidRequest_ShouldSaveBook() {
        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(createBookRequestDto);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void save_WhenTitleIsNull_ShouldThrowBookValidationException() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle(null);

        BookSaveException exception = assertThrows(BookSaveException.class, () -> {
            bookService.save(createBookRequestDto);
        });

        assertInstanceOf(BookValidationException.class, exception.getCause());
        assertEquals("Title can't be null", exception.getCause().getMessage());
    }

    @Test
    void findAll_WhenCalled_ShouldReturnListOfBooks() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> result = bookService.findAll(Pageable.unpaged());

        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());
    }

    @Test
    void findById_WhenExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(1L);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void findById_WhenNotExists_ShouldThrowBookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(1L);
        });

        assertEquals("Book with id: 1 not found", exception.getMessage());
    }

    @Test
    void deleteById_WhenExists_ShouldDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.deleteById(1L);

        verify(bookRepository).save(book);
        assertTrue(book.isDeleted());
    }

    @Test
    void deleteById_WhenNotExists_ShouldThrowBookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteById(1L);
        });

        assertEquals("Book with id: 1 not found", exception.getMessage());
    }

    @Test
    void updateBookById_WhenExists_ShouldUpdateBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).updateBookById(anyLong(), anyString(), anyString(), anyString(),
                any(), anyString(), anyString());

        bookService.updateBookById(1L, bookDto);

        verify(bookRepository).updateBookById(1L, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getIsbn(),
                bookDto.getPrice(), bookDto.getDescription(), bookDto.getCoverImage());
    }

    @Test
    void updateBookById_WhenNotExists_ShouldThrowBookNotFoundException() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookById(1L, bookDto);
        });
        assertEquals("Book not found with id 1", exception.getMessage());
    }

    @Test
    void search_WhenParametersAreValid_ShouldReturnListOfBooks() {
        BookSearchParametersDto searchParameters = new BookSearchParametersDto();
        searchParameters.setTitle(Collections.singletonList("Test Book"));

        Specification<Book> specification = (root, query,
                                             criteriaBuilder) -> criteriaBuilder.conjunction();
        when(bookSpecificationBuilder.build(searchParameters)).thenReturn(specification);

        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(eq(specification), any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> result = bookService.search(searchParameters, Pageable.unpaged());

        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());
    }
}
