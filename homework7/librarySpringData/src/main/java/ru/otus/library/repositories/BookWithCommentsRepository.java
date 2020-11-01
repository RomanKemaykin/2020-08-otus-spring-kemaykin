package ru.otus.library.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.models.BookWithComments;

public interface BookWithCommentsRepository extends CrudRepository<BookWithComments, Long> {
    BookWithComments findById(long Id);
}
