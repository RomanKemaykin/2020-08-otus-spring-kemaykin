package ru.otus.library.repositories;


import ru.otus.library.models.Genre;

public interface GenreRepository {
    Genre getById(long id);

    Genre getByName(String name);

    void save(Genre genre);
}
