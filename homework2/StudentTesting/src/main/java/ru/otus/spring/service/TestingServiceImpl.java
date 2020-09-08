package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.domain.TestingItem;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class TestingServiceImpl implements TestingService {
    // Fields
    @Value("${testingItemsFileName}")
    private String testingItemsFileName;

    private List<TestingItem> testingItems;

    @Value("${correctAnswersNumberForPass}")
    private Integer correctAnswersCountForPass;

    private Scanner inputSource = new Scanner(System.in);

    private List<StudentTest> studentTests;

    // Methods
    public TestingServiceImpl() {
        testingItems = new ArrayList<>();
        studentTests = new ArrayList<>();
    }

    public TestingServiceImpl(List<TestingItem> testingItems, Integer correctAnswersCountForPass) {
        this.testingItems = testingItems;
        this.correctAnswersCountForPass = correctAnswersCountForPass;
        studentTests = new ArrayList<>();
    }

    public List<TestingItem> getTestingItems() {
        return testingItems;
    }

    public void setTestingItems(List<TestingItem> testingItems) {
        this.testingItems = testingItems;
    }

    private String getTestingItemsFileName() {
        return testingItemsFileName;
    }

    private List<StudentTest> getStudentTests() {
        return studentTests;
    }

    private void prepareTestingItems() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(testingItemsFileName);
        File file = classPathResource.getFile();
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String lineData = fileScanner.nextLine();
            Scanner stringScanner = new Scanner(lineData);
            stringScanner.useDelimiter(",");
            String question = stringScanner.next();
            List<String> answers = new ArrayList<>();
            String rightAnswer = "";
            while (stringScanner.hasNext()) {
                String item = stringScanner.next();
                if (stringScanner.hasNext()) {
                    answers.add(item);
                } else {
                    rightAnswer = item;
                }
            }
            TestingItem testingItem = new TestingItem(question, answers, rightAnswer);
            testingItems.add(testingItem);
        }
    }

    public void printTestingItems() {
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            System.out.println(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                System.out.println(answers.get(j));
            }
            System.out.println(testingItems.get(i).getCorrectAnswer());
        }
    }

    public StudentTest testStudent(Scanner inputSource) {
        System.out.println("Enter your name: ");
        String name = inputSource.next();
        System.out.println("Enter your last name: ");
        String lastname = inputSource.next();
        Map<Integer, Boolean> answerResults = getAnswerResults(inputSource);
        Integer correctAnswersCount = getCorrectAnswersCount(answerResults);
        Boolean isTestPassed = correctAnswersCount >= correctAnswersCountForPass;
        StudentTest studentTest = new StudentTest(name, lastname, answerResults, isTestPassed);
        return studentTest;
    }

    private Map<Integer, Boolean> getAnswerResults(Scanner inputSource) {
        Map<Integer, Boolean> answerResults = new HashMap<>();
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            System.out.println(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                System.out.println(answers.get(j));
            }
            String answer = inputSource.next();
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

    @Override
    public void startStudentTesting() throws IOException {
        prepareTestingItems();

        StudentTest studentTest = testStudent(inputSource);
        studentTest.printTestingResults();
        studentTests.add(studentTest);
    }
}
