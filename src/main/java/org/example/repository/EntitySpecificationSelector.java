package org.example.repository;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface EntitySpecificationSelector <T> {
    String getKey();
    Specification<T> getSpecification(Map<String, Object> params);
}
