package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestingServiceImplTest {

    @Test
    void shouldHaveCorrectConstructor() {
        String testingItemsFileName = "11222";
        TestingServiceImpl testingService = new TestingServiceImpl(testingItemsFileName);
        Assertions.assertThat(testingService.getTestingItemsFileName()).isEqualTo(testingItemsFileName);
    }
}