package ru.otus.library.service;

public interface BookIOService {
    void listAll();

    void add();

    void showBookById(long id);

    void deleteBookById(long id);

    void modifyBookById(long id);
}
