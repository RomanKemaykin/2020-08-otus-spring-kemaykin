package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private String id;
    private String bookTitle;
    private String authorName;
    private String genreName;
}
