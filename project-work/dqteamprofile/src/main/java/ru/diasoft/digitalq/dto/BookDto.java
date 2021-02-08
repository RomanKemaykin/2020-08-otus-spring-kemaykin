package ru.diasoft.digitalq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private long id;
    private String bookTitle;
    private String authorName;
    private String genreName;
}
