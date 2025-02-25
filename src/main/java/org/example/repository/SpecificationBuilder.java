package org.example.repository;

import org.example.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder <T> {
    Specification<T> build(BookSearchParametersDto searchParameters);
}
