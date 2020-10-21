package ru.otus.library.repositories;

import ru.otus.library.models.BookComment;

import java.util.List;

public interface BookCommentRepository {
    void save(BookComment bookComment);

    BookComment getById(long id);

    List<BookComment> getListByBookId(long bookId);

    void update(BookComment bookComment);

    void delete(BookComment bookComment);
}
