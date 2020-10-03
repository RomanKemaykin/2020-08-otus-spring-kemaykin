package ru.otus.springBootstudentTesting.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.domain.TestingItem;
import ru.otus.springBootstudentTesting.service.IOService;
import ru.otus.springBootstudentTesting.service.QuestionService;
import ru.otus.springBootstudentTesting.service.TestingService;
import ru.otus.springBootstudentTesting.service.TestingServiceConsole;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class TestingItemDaoCsvTest {
    @MockBean
    private YamlProps props;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private IOService ioService;

    @MockBean
    private TestingItemInputStream testingItemInputStream;

    @MockBean
    private TestingService testingService;

    @MockBean
    private StudentTestDao studentTestDao;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private TestingItemDaoCsv testingItemDao;

    @Test
    void whenTwoRecordsInInputStreamFindAllShouldReturnExpectedCountOfTestingItems() {
        String inputData = "q1,a1,ca1" + System.lineSeparator() + "q2,a2,ca2";
        InputStream inputStream = new ByteArrayInputStream(inputData.getBytes());
        given(testingItemInputStream.inputStream()).willReturn(inputStream);
        List<TestingItem> testingItemList = testingItemDao.findAll();
        assertThat(testingItemList).hasSize(2);
    }

    @Test
    void findAllShouldReturnTestingItemsWithExpectedAttributes() {
        String inputData = "q1,a1,ca1";
        InputStream inputStream = new ByteArrayInputStream(inputData.getBytes());
        given(testingItemInputStream.inputStream()).willReturn(inputStream);
        List<TestingItem> testingItemList = testingItemDao.findAll();
        TestingItem expectedTestingItem = new TestingItem("q1", Arrays.asList("a1"), "ca1");
        assertThat(testingItemList.get(0)).isEqualToComparingFieldByField(expectedTestingItem);
    }

}