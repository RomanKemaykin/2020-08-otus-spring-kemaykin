package ru.otus.library.service;

import ru.otus.library.dto.BookCommentDto;

import java.util.List;

public interface BookCommentCrudService {
    BookCommentDto findById(long id);

    List<BookCommentDto> findAllByBookId(long bookId);

    void add(BookCommentDto bookCommentDto);

    void deleteById(long id);

    void modifyComment(BookCommentDto bookCommentDto);
}
