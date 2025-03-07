package org.example.repository;

public interface SpecificationProviderManager <T> {
    EntitySpecificationSelector<T> getSpecificationProvider(String key);
}
