package org.example.repository;

import org.example.config.CustomMySqlContainer;
import org.example.model.Book;
import org.example.model.Category;
import org.example.repository.book.BookRepository;
import org.example.repository.category.CategoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryTest.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    private Long bookId;
    private CustomMySqlContainer mySqlContainer;

    @BeforeAll
    void setUpContainer() {
        mySqlContainer = CustomMySqlContainer.getInstance();
        mySqlContainer.start();
    }

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("Sample Category");
        category.setDeleted(false);
        category = categoryRepository.save(category);

        Book book = new Book();
        book.setTitle("Sample Title");
        book.setAuthor("Sample Author");
        book.setIsbn("123456789");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Sample Description");
        book.setCoverImage("sampleImage.png");
        book.setDeleted(false);
        book.getCategories().add(category);

        book = bookRepository.save(book);
        bookId = book.getId();
    }

    @Test
    void testUpdateBookById() {
        Optional<Book> originalBook = bookRepository.findById(bookId);
        Assertions.assertTrue(originalBook.isPresent());

        bookRepository.updateBookById(
                bookId,
                "Updated Title",
                "Updated Author",
                "987654321",
                BigDecimal.valueOf(29.99),
                "Updated Description",
                "updatedImage.png"
        );

        entityManager.flush();
        entityManager.clear();

        Optional<Book> updatedBook = bookRepository.findById(bookId);
        Assertions.assertTrue(updatedBook.isPresent());

        Book book = updatedBook.get();

        Assertions.assertEquals("Updated Title", book.getTitle());
        Assertions.assertEquals("Updated Author", book.getAuthor());
        Assertions.assertEquals("987654321", book.getIsbn());
        Assertions.assertEquals(BigDecimal.valueOf(29.99), book.getPrice());
        Assertions.assertEquals("Updated Description", book.getDescription());
        Assertions.assertEquals("updatedImage.png", book.getCoverImage());
    }

    @Test
    void testExistsByIsbn() {
        Assertions.assertTrue(bookRepository.existsByIsbn("123456789"));
    }

    @Test
    void testFindByCategoriesId() {
        Long categoryId = categoryRepository.findAll().stream()
                .map(Category::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No category found for testing"));

        Page<Book> result = bookRepository.findByCategoriesId(categoryId, Pageable.unpaged());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @AfterAll
    void tearDownContainer() {
        mySqlContainer.stop();
    }
}
