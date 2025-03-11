package org.example.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.customAnnotations.UniqueIsbn;

import java.math.BigDecimal;

@Data
public class CreateBookRequestDto {
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String author;
    @NotNull
    @UniqueIsbn
    @NotBlank
    private String isbn;
    @NotNull
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
