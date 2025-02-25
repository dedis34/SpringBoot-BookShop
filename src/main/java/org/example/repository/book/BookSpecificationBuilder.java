package org.example.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookSearchParametersDto;
import org.example.model.Book;
import org.example.repository.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final BookSpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.getAuthor() != null && searchParameters.getAuthor().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParameters.getAuthor()));
        }

        if (searchParameters.getIsbn() != null && searchParameters.getIsbn().length > 0) {
                spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("isbn")
                        .getSpecification(searchParameters.getIsbn()));
        }

        if (searchParameters.getTitle() != null && searchParameters.getTitle().length > 0) {
                spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                        .getSpecification(searchParameters.getTitle()));
        }
        return spec;
    }
}
