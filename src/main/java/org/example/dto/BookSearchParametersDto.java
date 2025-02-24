package org.example.dto;

import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] title;
    private String[] author;
    private String[] isbn;
}
