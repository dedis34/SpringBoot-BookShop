package org.example.repository;

public interface SpecificationProviderManager <T> {
    SpecificationSelector<T> getSpecificationProvider(String key);
}
