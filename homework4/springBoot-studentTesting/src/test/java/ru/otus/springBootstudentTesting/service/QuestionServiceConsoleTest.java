package ru.otus.springBootstudentTesting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.StudentTestDao;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.TestingItem;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class QuestionServiceConsoleTest {
    @MockBean
    private YamlProps props;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private TestingItemDao testingItemDao;

    @MockBean
    private IOService ioService;

    @MockBean
    private TestingItemInputStream testingItemInputStream;

    @MockBean
    private StudentTestDao studentTestDao;

    @MockBean
    private TestingService testingService;

    @Autowired
    private QuestionServiceConsole questionServiceConsole;

    @Test
    void whenTwoAnswersGetAnswersResultsShouldReturnExpectedMap() {
        TestingItem testingItem = new TestingItem("q1", Arrays.asList("a1"), "ca1");
        TestingItem testingItem2 = new TestingItem("q2", Arrays.asList("a2"), "ca2");
        List<TestingItem> testingItems = new ArrayList<>();
        testingItems.add(testingItem);
        testingItems.add(testingItem2);
        given(testingItemDao.findAll()).willReturn(testingItems);
        given(ioService.readString()).willReturn("ca1");
        Map<Integer, Boolean> testingMap = questionServiceConsole.getAnswersResults();
        Map<Integer, Boolean> expectedMap = new HashMap<>();
        expectedMap.put(0, true);
        expectedMap.put(1, false);
        assertEquals(testingMap, expectedMap);
    }
}