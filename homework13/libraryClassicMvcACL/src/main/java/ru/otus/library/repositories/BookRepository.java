package ru.otus.library.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'ADMINISTRATION')")
    @Override
    Iterable<Book> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ') or hasPermission(returnObject, 'ADMINISTRATION')")
    Book getById(Long aLong);
}
