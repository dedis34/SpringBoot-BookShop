package org.example.repository;

import org.example.config.CustomMySqlContainer;
import org.example.model.Book;
import org.example.repository.book.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    private Long bookId;

    @BeforeEach
    void setUp() {
        CustomMySqlContainer.getInstance().start();

        Book book = new Book();
        book.setTitle("Sample Title");
        book.setAuthor("Sample Author");
        book.setIsbn("123456789");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Sample Description");
        book.setCoverImage("sampleImage.png");
        book.setDeleted(false);
        System.out.println("SetUp -> isDeleted: " + book.isDeleted());

        book = bookRepository.save(book);
        bookId = book.getId();
    }

    @Test
    void testUpdateBookById() {
        Optional<Book> originalBook = bookRepository.findById(bookId);
        Assertions.assertTrue(originalBook.isPresent());
        System.out.println("Before update -> isDeleted: " + originalBook.get().isDeleted());

        String updatedTitle = "Updated Title";
        String updatedAuthor = "Updated Author";
        String updatedIsbn = "987654321";
        BigDecimal updatedPrice = BigDecimal.valueOf(29.99);
        String updatedDescription = "Updated Description";
        String updatedCoverImage = "updatedImage.png";

        System.out.println("Calling updateBookById with:");
        System.out.println(" - id: " + bookId);
        System.out.println(" - title: " + updatedTitle);
        System.out.println(" - author: " + updatedAuthor);
        System.out.println(" - isbn: " + updatedIsbn);
        System.out.println(" - price: " + updatedPrice);
        System.out.println(" - description: " + updatedDescription);
        System.out.println(" - coverImage: " + updatedCoverImage);

        bookRepository.updateBookById(
                bookId,
                updatedTitle,
                updatedAuthor,
                updatedIsbn,
                updatedPrice,
                updatedDescription,
                updatedCoverImage
        );

        entityManager.flush();
        entityManager.clear();

        Optional<Book> updatedBook = bookRepository.findById(bookId);
        Assertions.assertTrue(updatedBook.isPresent());

        Book book = updatedBook.get();

        System.out.println("After update -> title: " + book.getTitle());

        Assertions.assertEquals(updatedTitle, book.getTitle());
        Assertions.assertEquals(updatedAuthor, book.getAuthor());
        Assertions.assertEquals(updatedIsbn, book.getIsbn());
        Assertions.assertEquals(updatedPrice, book.getPrice());
        Assertions.assertEquals(updatedDescription, book.getDescription());
        Assertions.assertEquals(updatedCoverImage, book.getCoverImage());
    }

    @Test
    void testExistsByIsbn() {
        String isbn = "123456789";
        Assertions.assertTrue(bookRepository.existsByIsbn(isbn));
    }

    @Test
    void testFindByCategoriesId() {
        Long categoryId = 1L;
        Assertions.assertNotNull(bookRepository.findByCategoriesId(categoryId, Pageable.unpaged()));
    }
}
