package ru.otus.library.service;

public interface BookCrudService {
    void listAll();

    void add();

    void showBookById(long id);

    void deleteBookById(long id);

    void modifyBookById(long id);
}
