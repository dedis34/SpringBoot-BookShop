package org.example.repository.book.spec;

import org.example.model.Book;
import org.example.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get("isbn").in(Arrays.stream(params).toArray());
    }
}
