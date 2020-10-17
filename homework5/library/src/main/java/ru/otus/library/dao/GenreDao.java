package ru.otus.library.dao;


import ru.otus.library.domain.Genre;

public interface GenreDao {
    Genre getById(long id);

    Genre getByName(String name);

    long insert(Genre genre);
}
