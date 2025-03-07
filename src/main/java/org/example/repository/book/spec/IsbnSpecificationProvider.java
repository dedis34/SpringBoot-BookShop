package org.example.repository.book.spec;

import org.example.model.Book;
import org.example.repository.EntitySpecificationSelector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class IsbnSpecificationProvider implements EntitySpecificationSelector<Book> {
    private static final Logger logger = Logger.getLogger(IsbnSpecificationProvider.class.getName());

    @Override
    public String getKey() {
        return "isbn";
    }

    @Override
    public Specification<Book> getSpecification(Map<String, Object> params) {
        if (params == null || !params.containsKey("isbn")) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.conjunction();
        }

        Object isbnParam = params.get("isbn");

        if (isbnParam instanceof String[] && ((String[]) isbnParam).length > 0) {
            return (root, query, criteriaBuilder)
                    -> root.get("isbn").in((Object[]) isbnParam);
        }

        if (isbnParam != null) {
            logger.warning("Unexpected data type for 'isbn' parameter. "
                    + "Expected String[], but found: " + isbnParam.getClass().getName());
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction();
    }
}
