package org.example.repository.book.spec;

import org.example.model.Book;
import org.example.repository.spec.EntitySpecificationSelector;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class TitleSpecificationProvider implements EntitySpecificationSelector<Book> {
    private static final Logger logger = Logger.getLogger(TitleSpecificationProvider.class.getName());

    @Override
    public String getKey() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(Map<String, Object> params) {
        if (params == null || !params.containsKey("title")) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.conjunction();
        }

        Object titleParam = params.get("title");

        if (titleParam instanceof String[] && ((String[]) titleParam).length > 0) {
            return (root, query, criteriaBuilder)
                    -> root.get("title").in((Object[]) titleParam);
        }

        if (titleParam != null) {
            logger.warning("Unexpected data type for 'title' parameter. "
                    + "Expected String[], but found: " + titleParam.getClass().getName());
        }

        return (root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction();
    }
}
