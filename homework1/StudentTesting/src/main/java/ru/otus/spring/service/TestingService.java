package ru.otus.spring.service;

import ru.otus.spring.domain.TestingItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TestingService {
    public void prepareTestingItems() throws IOException;
    public void printTestingItems();
}
