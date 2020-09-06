package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.TestingItem;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestingServiceImplTest {
    private TestingServiceImpl testingService;

    @BeforeEach
    void setUp() {
        testingService = new TestingServiceImpl();
    }

    @Test
    void getAndSetTestingItemsShouldCorrect() {
        List<TestingItem> testingItemList = new ArrayList<>();
        testingService.setTestingItems(testingItemList);
        Assertions.assertThat(testingService.getTestingItems()).isEqualTo(testingItemList);
    }

}