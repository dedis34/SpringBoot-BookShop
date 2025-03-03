package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;

@Data
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    @UniqueElements
    private String isbn;
    @NotNull
    private BigDecimal price;
    private String description;
    private String coverImage;
}
