package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library.models.Author;
public interface AuthorRepository extends MongoRepository<Author, String> {
    Author findByName(String name);
}
