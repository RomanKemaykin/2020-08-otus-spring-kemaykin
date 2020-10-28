package ru.otus.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre getById(long id);

    Genre getByName(String name);

    Genre save(Genre genre);
}
