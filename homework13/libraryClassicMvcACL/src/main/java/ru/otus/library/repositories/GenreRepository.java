package ru.otus.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String name);
}
