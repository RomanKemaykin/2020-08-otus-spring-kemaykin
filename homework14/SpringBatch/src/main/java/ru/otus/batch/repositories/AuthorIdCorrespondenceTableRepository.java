package ru.otus.batch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.destination.models.AuthorIdCorrespondenceTable;

public interface AuthorIdCorrespondenceTableRepository extends MongoRepository<AuthorIdCorrespondenceTable, String> {
    AuthorIdCorrespondenceTable findBySourceId(long sourceId);
}
