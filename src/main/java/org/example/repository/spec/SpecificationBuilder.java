package org.example.repository.spec;

import org.example.dto.book.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder <T> {
    Specification<T> build(BookSearchParametersDto searchParameters);
}
