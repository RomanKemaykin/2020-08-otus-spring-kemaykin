package ru.otus.library.repositories;

import ru.otus.library.models.BookCommentWithBook;

import java.util.List;

public interface BookCommentRepository {
    void save(BookCommentWithBook bookCommentWithBook);

    BookCommentWithBook getById(long id);

    void update(BookCommentWithBook bookCommentWithBook);

    void delete(BookCommentWithBook bookCommentWithBook);
}
