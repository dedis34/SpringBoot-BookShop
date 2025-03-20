package org.example.service.impl;

import lombok.RequiredArgsConstructor;
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
import org.example.service.BookService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        try {
            if (createBookRequestDto.getTitle() == null) {
                throw new BookValidationException("Title can't be null");
            }

            Book book = bookMapper.toModel(createBookRequestDto);
            Book savedBook = bookRepository.save(book);

            return bookMapper.toDto(savedBook);

        } catch (Exception e) {
            throw new BookSaveException("Can't save book", e);
        }
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }


    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public void updateBookById(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with id " + id);
        }
        bookRepository.updateBookById(id, bookDto.getTitle(),
                bookDto.getAuthor(), bookDto.getIsbn(),
                bookDto.getPrice(), bookDto.getDescription(),
                bookDto.getCoverImage());
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
