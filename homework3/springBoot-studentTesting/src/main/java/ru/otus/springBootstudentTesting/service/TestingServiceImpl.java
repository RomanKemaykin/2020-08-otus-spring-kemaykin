package ru.otus.springBootstudentTesting.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.StudentTest;
import ru.otus.springBootstudentTesting.domain.TestingItem;

import java.util.*;

@Service
public class TestingServiceImpl implements TestingService {
    // Fields
    private final YamlProps props;

    private List<TestingItem> testingItems;

    public Integer getCorrectAnswersCountForPass() {
        return correctAnswersCountForPass;
    }

    private final Integer correctAnswersCountForPass;

    private List<StudentTest> studentTests;

    private final MessageSource messageSource;

    private final IOService ioService;

    private final TestingItemDao testingItemDao;

    // Methods
    public TestingServiceImpl(YamlProps props, MessageSource messageSource, IOService ioService, TestingItemDao testingItemDao) {
        studentTests = new ArrayList<>();
        this.props = props;
        this.correctAnswersCountForPass = props.getCorrectAnswersCountForPass();
        this.messageSource = messageSource;
        this.ioService = ioService;
        this.testingItemDao = testingItemDao;
        testingItems = testingItemDao.findAll();
    }

    public List<TestingItem> getTestingItems() {
        return testingItems;
    }

    public StudentTest testStudent() {
        String s = messageSource.getMessage("enter.your.name", new String[]{}, props.getLocale());
        ioService.out(s);
        String name = ioService.readString();
        s = messageSource.getMessage("enter.your.last.name", new String[]{}, props.getLocale());
        ioService.out(s);
        String lastname = ioService.readString();
        Map<Integer, Boolean> answerResults = getAnswerResults();
        Integer correctAnswersCount = getCorrectAnswersCount(answerResults);
        Boolean isTestPassed = correctAnswersCount >= correctAnswersCountForPass;
        StudentTest studentTest = new StudentTest(name, lastname, answerResults, isTestPassed);
        return studentTest;
    }

    public List<StudentTest> getStudentTests() {
        return studentTests;
    }

    public void printTestingItems() {
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            ioService.out(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                ioService.out(answers.get(j));
            }
            ioService.out(testingItems.get(i).getCorrectAnswer());
        }
    }

    private Map<Integer, Boolean> getAnswerResults() {
        Map<Integer, Boolean> answerResults = new HashMap<>();
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            ioService.out(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                ioService.out(answers.get(j));
            }
            String answer = ioService.readString();
            Boolean answerResult = answer.contentEquals(testingItem.getCorrectAnswer());
            answerResults.put(i, answerResult);
        }
        return answerResults;
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

    @Override
    public void runStudentTesting() {
        StudentTest studentTest = testStudent();
        printStudentTestingResults(studentTest);
        studentTests.add(studentTest);
    }
}
