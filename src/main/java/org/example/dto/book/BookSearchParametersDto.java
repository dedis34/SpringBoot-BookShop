package org.example.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class BookSearchParametersDto {
    private List<String> title;
    private List<String> author;
    private List<String> isbn;
}
