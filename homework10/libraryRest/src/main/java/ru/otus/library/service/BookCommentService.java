package ru.otus.library.service;

import ru.otus.library.dto.BookCommentDto;

import java.util.List;

public interface BookCommentService {
    BookCommentDto findById(String id);

    List<BookCommentDto> findAllByBookId(String bookId);

    void add(BookCommentDto bookCommentDto);

    void deleteById(String id);

    void modifyComment(BookCommentDto bookCommentDto);
}
