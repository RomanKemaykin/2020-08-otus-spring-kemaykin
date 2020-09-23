package ru.otus.springBootstudentTesting.dao;

import org.springframework.stereotype.Component;
import ru.otus.springBootstudentTesting.domain.TestingItem;
import ru.otus.springBootstudentTesting.utilities.TestingItemInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TestingItemDaoCsv implements TestingItemDao {
    private final TestingItemInputStream testingItemInputStream;

    public TestingItemDaoCsv(TestingItemInputStream testingItemInputStream) {
        this.testingItemInputStream = testingItemInputStream;
    }

    @Override
    public List<TestingItem> findAll() {
        List<TestingItem> testingItems = new ArrayList<>();

        InputStream inputStream = testingItemInputStream.inputStream();
        Scanner fileScanner = new Scanner(inputStream);

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
        return testingItems;
    }
}
