package org.example.customAnnotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.repository.book.BookRepository;
import org.example.customAnnotations.UniqueIsbn;

@RequiredArgsConstructor
public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {
    private final BookRepository bookRepository;

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isEmpty()) {
            return true;
        }

        return !bookRepository.existsByIsbn(isbn);
    }
}
