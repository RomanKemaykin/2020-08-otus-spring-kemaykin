package ru.otus.library.service;

import ru.otus.library.dto.BookDto;

import java.util.List;

public interface BookCrudService {
    List<BookDto> findAll();

    void add(BookDto bookDto);

    BookDto findBookById(long id);

    void deleteBookById(long id);

    void modifyBook(BookDto bookDto);
}
