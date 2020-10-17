package ru.otus.library.repositories;

import ru.otus.library.models.Book;

import java.util.List;

public interface BookRepository {
    Book getById(long id);

    List<Book> getAll();

    void save(Book book);

    void update(Book book);

    void delete(long id);
}
