package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.models.User;

public interface UserRepository extends MongoRepository<User, String> {
}
