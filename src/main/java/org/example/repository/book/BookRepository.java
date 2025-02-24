package org.example.repository.book;

import org.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Modifying
    @Query("UPDATE Book b SET "
            + " b.title = :title,"
            + " b.author = :author,"
            + " b.isbn = :isbn,"
            + " b.price = :price,"
            + " b.description = :description,"
            + " b.coverImage = :coverImage WHERE b.id = :id AND b.isDeleted = false")
    void updateBookById(Long id, String title, String author, String isbn,
                       BigDecimal price, String description, String coverImage);
}
