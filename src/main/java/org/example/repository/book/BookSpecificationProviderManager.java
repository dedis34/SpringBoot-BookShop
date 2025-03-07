package org.example.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.exception.EntitySpecificationProviderException;
import org.example.repository.EntitySpecificationSelector;
import org.example.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager<Book> implements SpecificationProviderManager<Book> {

    private final List<EntitySpecificationSelector<Book>> specificationProviders;

    @Override
    public EntitySpecificationSelector<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new EntitySpecificationProviderException("Can't find correct "
                        + "specification provider for " + key));
    }
}
