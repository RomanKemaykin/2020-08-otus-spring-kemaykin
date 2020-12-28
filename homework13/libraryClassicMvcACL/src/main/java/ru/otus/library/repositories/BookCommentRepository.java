package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;

public interface BookCommentRepository extends CrudRepository<BookComment, Long> {
    void deleteAllByBook(Book book);
}
