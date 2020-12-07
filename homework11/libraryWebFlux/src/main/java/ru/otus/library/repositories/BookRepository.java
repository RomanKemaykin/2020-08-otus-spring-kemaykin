package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.models.Book;

import java.util.List;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
