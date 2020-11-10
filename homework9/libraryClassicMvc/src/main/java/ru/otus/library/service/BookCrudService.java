package ru.otus.library.service;

import ru.otus.library.dto.BookDto;

import java.util.List;

public interface BookCrudService {
    List<BookDto> findAll();

    void add(BookDto bookDto);

    BookDto findBookById(String id);

    void deleteBookById(String id);

    void modifyBook(BookDto bookDto);
}
