package ru.otus.spring.service;

import java.io.IOException;

public interface TestingService {
    public void prepareTestingItems() throws IOException;

    public void printTestingItems();
}
