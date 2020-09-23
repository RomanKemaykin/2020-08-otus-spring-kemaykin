package ru.otus.springBootstudentTesting.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.StudentTest;
import ru.otus.springBootstudentTesting.domain.TestingItem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TestingServiceImplTest {
/*
    @Configuration
    static class TestConfiguration {
*/
/*
        @Bean
        public IOServiceConsole ioServiceConsole() {
            return new IOServiceConsole();
        }
*//*

    }
*/
    @Mock
    private YamlProps props;

    @Mock
    private MessageSource messageSource;

    @Mock
    private TestingItemDao testingItemDao;

    @Mock
    private IOServiceConsole ioService;

    @Autowired
    TestingServiceImpl testingService;


    @BeforeEach
    void setUp() {
        List<TestingItem> testingItems = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        answers.add("a1");
        TestingItem testingItem;
        testingItem = new TestingItem("q1", answers, "4");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q2", answers, "4");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q3", answers, "7");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q4", answers, "5");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q5", answers, "6");
        testingItems.add(testingItem);
        given(testingItemDao.findAll()).willReturn(testingItems);

        given(props.getCorrectAnswersCountForPass()).willReturn(3);
        given(ioService.readString()).willReturn("string111");
        given(ioService.readString()).willReturn("string112");

        InputStream savedIn = System.in;
        String inputDataForTest = "r" + System.lineSeparator() + "k"
                + "4" + System.lineSeparator()
                + "4" + System.lineSeparator()
                + "7" + System.lineSeparator()
                + "5" + System.lineSeparator()
                + "7" + System.lineSeparator()
                + "7" + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(inputDataForTest.getBytes());
        System.setIn(inputStream);
//        given(ioService.)
    }


    @Test
    void testStudentMethodShouldReturnCorrectStructure() {
        testingService.printTestingItems();
        System.out.println(testingService.getCorrectAnswersCountForPass());
        StudentTest studentTest = testingService.testStudent();

        // verify()
        //assertThat(studentTest).isNotNull();
    }
}