package ru.otus.springBootstudentTesting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.StudentTestDao;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.StudentTest;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TestingServiceConsoleTest {
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
    private StudentTestDao studentTestDao;

    @Autowired
    private TestingServiceConsole testingService;

    @BeforeEach
    void setUp() {
        studentTestDao.removeAll();
    }

    @Test
    void runStudentTestingMethodShouldSaveOneObjectInDaoWithExpectedAttribute() {
        testingService.runStudentTesting("Ivan", "Petrov");
        List<StudentTest> studentTests = studentTestDao.findALL();
        System.out.println(studentTests.toString());
        System.out.println(studentTests.size());
        assertThat(studentTests.size()).isEqualTo(1);
        assertThat(studentTests.get(0).getName()).isEqualTo("Ivan");
    }
}