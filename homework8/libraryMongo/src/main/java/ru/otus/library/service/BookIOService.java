package ru.otus.library.service;

public interface BookIOService {
    void listAll();

    void add();

    void showBookById(String id);

    void deleteBookById(String id);

    void modifyBookById(String id);
}
