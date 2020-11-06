package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {
    List<BookComment> findAllByBook(Book book);

    void deleteAllByBook(Book book);
}
