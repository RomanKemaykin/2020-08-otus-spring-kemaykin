package ru.otus.springBootstudentTesting.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.StudentTestDao;
import ru.otus.springBootstudentTesting.domain.StudentTest;

import java.util.*;

@Service
public class TestingServiceConsole implements TestingService {
    // Fields
    private final YamlProps props;

    private final MessageSource messageSource;

    private final IOService ioService;

    private final QuestionService questionService;

    private final StudentTestDao dao;

    private final Integer correctAnswersCountForPass;

    // Methods

    public TestingServiceConsole(YamlProps props, MessageSource messageSource, IOService ioService, QuestionService questionService, StudentTestDao dao) {
        this.props = props;
        this.messageSource = messageSource;
        this.ioService = ioService;
        this.questionService = questionService;
        this.correctAnswersCountForPass = props.getCorrectAnswersCountForPass();
        this.dao = dao;
    }

    @Override
    public void runStudentTesting(String name, String lastName) {
        // todo проверить что студент еще не проходил тест - начитать через ДАО есть ли у него сданный тест,...
        StudentTest studentTest = testStudent(name, lastName);
        printStudentTestingResults(studentTest);
        dao.save(studentTest);
    }

    private Integer getCorrectAnswersCount(Map<Integer, Boolean> answerResults) {
        Integer correctAnswersCount = 0;
        for (int i = 0; i < answerResults.size(); i++) {
            if (answerResults.get(i) == true) {
                correctAnswersCount++;
            }
        }
        return correctAnswersCount;
    }

    private void printStudentTestingResults(StudentTest studentTest) {
        String resultText = studentTest.getName() + ' ' + studentTest.getLastName();
        ioService.out(resultText);

        if (studentTest.getTestPassed()) {
            resultText = messageSource.getMessage("you.passed.the.test", new String[]{}, props.getLocale());
        } else {
            resultText = messageSource.getMessage("you.have.not.passed.the.test", new String[]{}, props.getLocale());
        }
        ioService.out(resultText);
    }

    private StudentTest testStudent(String name, String lastName) {
        Map<Integer, Boolean> answerResults = questionService.getAnswersResults();
        Integer correctAnswersCount = getCorrectAnswersCount(answerResults);
        Boolean isTestPassed = correctAnswersCount >= correctAnswersCountForPass;
        StudentTest studentTest = new StudentTest(name, lastName, answerResults, isTestPassed);
        return studentTest;
    }
}
