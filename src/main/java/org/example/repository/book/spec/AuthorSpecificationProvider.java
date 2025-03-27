package org.example.repository.book.spec;

import org.example.model.Book;
import org.example.repository.spec.EntitySpecificationSelector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class AuthorSpecificationProvider implements EntitySpecificationSelector<Book> {
    private static final Logger logger = Logger.getLogger(AuthorSpecificationProvider.class.getName());

    @Override
    public String getKey() {
        return "author";
    }

    @Override
    public Specification<Book> getSpecification(Map<String, Object> params) {
        if (params == null || !params.containsKey("author")) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.conjunction();
        }

        Object authorParam = params.get("author");

        if (authorParam instanceof String[] && ((String[]) authorParam).length > 0) {
            return (root, query, criteriaBuilder)
                    -> root.get("author").in((Object[]) authorParam);
        }

        if (authorParam != null) {
            logger.warning("Unexpected data type for 'author' parameter. "
                    + "Expected String[], but found: " + authorParam.getClass().getName());
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction();
    }
}
