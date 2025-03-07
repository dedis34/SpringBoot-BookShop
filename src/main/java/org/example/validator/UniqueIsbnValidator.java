package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isEmpty()) {
            return true;
        }

        return !bookRepository.existsByIsbn(isbn);
    }
}
