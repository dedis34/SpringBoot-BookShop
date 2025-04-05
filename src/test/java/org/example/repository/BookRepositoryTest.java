package org.example.repository;

import org.example.model.Book;
import org.example.repository.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Test Description");
        book.setCoverImage("test.jpg");
    }

    @Test
    void findById_WhenBookExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test
    void save_WhenBookIsValid_ShouldPersistBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookRepository.save(book);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void updateBookById_WhenBookExists_ShouldUpdateBook() {
        doNothing().when(bookRepository).updateBookById(anyLong(), anyString(), anyString(), anyString(),
                any(), anyString(), anyString());

        bookRepository.updateBookById(1L, "Updated Title", "Updated Author", "0987654321",
                BigDecimal.valueOf(29.99), "Updated Description", "updated.jpg");

        verify(bookRepository, times(1)).updateBookById(1L, "Updated Title",
                "Updated Author", "0987654321", BigDecimal.valueOf(29.99),
                "Updated Description", "updated.jpg");
    }

    @Test
    void existsByIsbn_WhenIsbnExists_ShouldReturnTrue() {
        when(bookRepository.existsByIsbn("1234567890")).thenReturn(true);

        boolean exists = bookRepository.existsByIsbn("1234567890");

        assertTrue(exists);
    }

    @Test
    void findByCategoriesId_WhenCategoryExists_ShouldReturnBooks() {
        Pageable pageable = Pageable.unpaged();
        when(bookRepository.findByCategoriesId(1L,
                pageable)).thenReturn(new PageImpl<>(Collections.singletonList(book)));

        Page<Book> result = bookRepository.findByCategoriesId(1L, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Book", result.getContent().getFirst().getTitle());
    }
}
