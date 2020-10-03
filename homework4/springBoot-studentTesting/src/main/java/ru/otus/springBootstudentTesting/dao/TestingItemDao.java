package ru.otus.springBootstudentTesting.dao;

import ru.otus.springBootstudentTesting.domain.TestingItem;

import java.util.List;

public interface TestingItemDao {
    List<TestingItem> findAll();
}
