package ru.otus.springBootstudentTesting.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.domain.StudentTest;
import ru.otus.springBootstudentTesting.service.IOService;
import ru.otus.springBootstudentTesting.service.QuestionService;
import ru.otus.springBootstudentTesting.service.TestingServiceConsole;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentTestDaoSimpleListTest {
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
    private QuestionService questionService;

    @Autowired
    TestingServiceConsole testingService;

    @Autowired
    private StudentTestDaoSimpleList dao;

    private static final StudentTest STUDENT_TEST_IVAN = new StudentTest("Ivan", "Petrov", new HashMap<>(), true);
    private static final StudentTest STUDENT_TEST_PAVEL = new StudentTest("Pavel", "Ivanov", new HashMap<>(), true);

    @Test
    void withoutSavingFindALLShouldReturnEmptyList() {
        List<StudentTest> studentTests = dao.findALL();
        assertThat(studentTests).isEmpty();
    }

    @Test
    void afterSaveOneObjectsListShouldContainOneObjectWithExpectedAttributes() {
        dao.save(STUDENT_TEST_IVAN);
        List<StudentTest> studentTests = dao.findALL();
        assertThat(studentTests.get(0)).isEqualToComparingFieldByField(STUDENT_TEST_IVAN);
    }

    @Test
    void afterSaveTwoObjectsListShouldContainTwoObjects() {
        dao.save(STUDENT_TEST_IVAN);
        dao.save(STUDENT_TEST_PAVEL);
        List<StudentTest> studentTests = dao.findALL();
        assertThat(studentTests).hasSize(2);
    }

}