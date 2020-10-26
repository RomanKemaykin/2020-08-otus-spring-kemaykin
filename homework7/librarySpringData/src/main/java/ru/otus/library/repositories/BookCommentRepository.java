package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.BookCommentWithBook;

public interface BookCommentRepository extends CrudRepository<BookCommentWithBook, Long> {
    BookCommentWithBook save(BookCommentWithBook bookCommentWithBook);

    BookCommentWithBook getById(long id);

    void delete(BookCommentWithBook bookCommentWithBook);
}
