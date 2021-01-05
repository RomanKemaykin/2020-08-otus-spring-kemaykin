package ru.otus.batch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.batch.destination.models.AuthorIdCorrespondenceTable;
import ru.otus.batch.destination.models.GenreIdCorrespondenceTable;

public interface GenreIdCorrespondenceTableRepository extends MongoRepository<GenreIdCorrespondenceTable, String> {
    GenreIdCorrespondenceTable findBySourceId(long sourceId);
}
