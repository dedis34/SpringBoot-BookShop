package org.example.repository.spec;

public interface SpecificationProviderManager <T> {
    EntitySpecificationSelector<T> getSpecificationProvider(String key);
}
