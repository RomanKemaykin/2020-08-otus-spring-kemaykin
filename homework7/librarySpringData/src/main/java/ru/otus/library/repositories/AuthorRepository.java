package ru.otus.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getById(long id);
    Author getByName(String name);
    Author save(Author author);
}
