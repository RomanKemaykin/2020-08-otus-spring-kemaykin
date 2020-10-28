package ru.otus.library.repositories;

import ru.otus.library.models.Author;

public interface AuthorRepository {
    Author getById(long id);
    Author getByName(String name);
    void save(Author author);
}
