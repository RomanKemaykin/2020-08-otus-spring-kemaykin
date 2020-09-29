package ru.otus.springBootstudentTesting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.StudentTest;
import ru.otus.springBootstudentTesting.domain.TestingItem;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TestingServiceImplTest {
    @MockBean
    private YamlProps props;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private TestingItemDao testingItemDao;

    @MockBean
    private IOServiceConsole ioService;

    @MockBean
    private TestingItemInputStream testingItemInputStream;

    @Autowired
    TestingServiceImpl testingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testStudentMethodShouldReturnExpectedStructure() {
        StudentTest studentTest = testingService.testStudent();
        assertThat(studentTest).isNotNull();
    }
}