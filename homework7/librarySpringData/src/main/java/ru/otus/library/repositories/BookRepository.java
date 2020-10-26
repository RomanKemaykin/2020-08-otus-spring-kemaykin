package ru.otus.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookWithComments;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book getById(long id);

    List<Book> findAll();

    Book save(Book book);

    void delete(Book book);
}
