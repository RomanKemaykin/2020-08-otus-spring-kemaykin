package ru.otus.library.service;

public interface BookCommentCrudService {
    void listAllByBookId(long bookId);

    void add(long id);

    void deleteById(long id);

    void modifyCommentById(long id);
}
