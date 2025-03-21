package org.example.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.dto.book.BookSearchParametersDto;
import org.example.model.Book;
import org.example.repository.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final BookSpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Map<String, Object> params = new HashMap<>();

        if (searchParameters.getAuthor() != null && searchParameters.getAuthor().length > 0) {
            params.put("author", searchParameters.getAuthor());
        }

        if (searchParameters.getIsbn() != null && searchParameters.getIsbn().length > 0) {
            params.put("isbn", searchParameters.getIsbn());
        }

        if (searchParameters.getTitle() != null && searchParameters.getTitle().length > 0) {
            params.put("title", searchParameters.getTitle());
        }

        Specification<Book> spec = Specification.where(null);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(entry.getKey())
                    .getSpecification(params));
        }

        return spec;
    }
}
