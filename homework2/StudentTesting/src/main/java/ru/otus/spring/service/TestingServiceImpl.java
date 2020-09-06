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
    public String getTestingItemsFileName() {
        return testingItemsFileName;
    }

    @Value("${testingItemsFileName}")
    private String testingItemsFileName;
    private List<TestingItem> testingItems;
    @Value("${correctAnswersNumberForPass}")
    private Integer correctAnswersNumberForPass;

    public TestingServiceImpl() {
        testingItems = new ArrayList<>();
        studentTests = new ArrayList<>();
    }

    public List<TestingItem> getTestingItems() {
        return testingItems;
    }

    public void setTestingItems(List<TestingItem> testingItems) {
        this.testingItems = testingItems;
    }

    public List<StudentTest> getStudentTests() {
        return studentTests;
    }

    private List<StudentTest> studentTests;

    public void prepareTestingItems() throws IOException {
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

    public void testStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.next();
        System.out.println("Enter your last name: ");
        String lastname = scanner.next();
        Map<Integer, Boolean> answerResults = new HashMap<>();
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            System.out.println(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                System.out.println(answers.get(j));
            }
            String answer = scanner.next();
            Boolean answerResult = answer.contentEquals(testingItem.getCorrectAnswer());
            answerResults.put(i, answerResult);
        }
        StudentTest studentTest = new StudentTest(name, lastname, answerResults, correctAnswersNumberForPass);
        studentTest.printTestingResults();
        studentTests.add(studentTest);
    }

    public void startStudentTesting() throws IOException {
        prepareTestingItems();
        testStudent();
    }
}
