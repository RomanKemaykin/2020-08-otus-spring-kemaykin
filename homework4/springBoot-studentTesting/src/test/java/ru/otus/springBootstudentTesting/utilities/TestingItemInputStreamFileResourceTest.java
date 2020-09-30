package ru.otus.springBootstudentTesting.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.StudentTestDao;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.service.IOService;
import ru.otus.springBootstudentTesting.service.QuestionService;
import ru.otus.springBootstudentTesting.service.QuestionServiceConsole;
import ru.otus.springBootstudentTesting.service.TestingServiceConsole;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class TestingItemInputStreamFileResourceTest {
    @MockBean
    private YamlProps props;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private TestingItemDao testingItemDao;

    @MockBean
    private IOService ioService;

    @MockBean
    private StudentTestDao studentTestDao;

    @MockBean
    private TestingServiceConsole testingService;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private TestingItemInputStreamFileResource testingItemInputStreamFileResource;

    @Test
    void forFileWithTwoLinesInputStreamShouldReturnInputStreamWithExpectedContent() {
        given(props.getLocale()).willReturn(new Locale("en", "EN"));
        given(props.getTestingItemsFileName()).willReturn("testingItems.csv");
        InputStream testingInputStream = testingItemInputStreamFileResource.inputStream();
        String inputData = "q1,a1,ca1" + System.lineSeparator() + "q2,a2,ca2";
        InputStream expectedInputStream = new ByteArrayInputStream(inputData.getBytes());
        assertThat(testingInputStream)
                .isNotNull()
                .hasSameContentAs(expectedInputStream);
    }
}