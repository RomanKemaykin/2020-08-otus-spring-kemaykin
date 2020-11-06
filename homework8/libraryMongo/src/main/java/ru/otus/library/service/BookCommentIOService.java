package ru.otus.library.service;

public interface BookCommentIOService {
    void listAllByBookId(String bookId);

    void add(String id);

    void deleteById(String id);

    void modifyCommentById(String id);
}
