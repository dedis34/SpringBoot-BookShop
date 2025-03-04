package org.example.repository.book.spec;

import org.example.model.Book;
import org.example.repository.SpecificationSelector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthorSpecificationProvider implements SpecificationSelector<Book> {
    @Override
    public String getKey() {
        return "author";
    }

    @Override
    public Specification<Book> getSpecification(Map<String, Object> params) {
        if (params == null || !params.containsKey("author") || ((String[]) params.get("author")).length == 0) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder)
                -> root.get("author").in((Object[]) params.get("author"));
    }
}
