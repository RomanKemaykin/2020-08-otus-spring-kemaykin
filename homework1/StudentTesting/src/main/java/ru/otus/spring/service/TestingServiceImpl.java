package ru.otus.spring.service;

import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.TestingItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestingServiceImpl implements TestingService {
    private String testingItemsFileName;
    private List<TestingItem> testingItems;

    public TestingServiceImpl(String testingItemsFileName) {
        this.testingItemsFileName = testingItemsFileName;
        testingItems = new ArrayList<>();
    }

    public List<TestingItem> getTestingItems() {
        return testingItems;
    }

    public void setTestingItems(List<TestingItem> testingItems) {
        this.testingItems = testingItems;
    }

    public void prepareTestingItems() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(testingItemsFileName);
        File file = classPathResource.getFile();
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String lineData = fileScanner.nextLine();
            Scanner stringScanner = new Scanner(lineData);
            stringScanner.useDelimiter(",");
            TestingItem testingItem = new TestingItem();
            testingItem.setQuestion(stringScanner.next());
            while (stringScanner.hasNext()) {
                String item = stringScanner.next();
                testingItem.addAnswer(item);
            }
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
        }
    }
}
