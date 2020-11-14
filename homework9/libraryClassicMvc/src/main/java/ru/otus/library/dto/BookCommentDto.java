package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class BookCommentDto {
    private String id;
    private String bookId;
    private String bookTitle;
    private String bookComment;
}
