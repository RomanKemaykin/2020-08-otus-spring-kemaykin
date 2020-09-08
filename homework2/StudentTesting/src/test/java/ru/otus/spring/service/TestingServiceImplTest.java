package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.StudentTest;
import ru.otus.spring.domain.TestingItem;

import java.sql.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestingServiceImplTest {
    private TestingServiceImpl testingService;

    @BeforeEach
    void setUp() {
        testingService = new TestingServiceImpl();
    }

    @Test
    void getAndSetTestingItemsShouldCorrect() {
        List<TestingItem> testingItemList = new ArrayList<>();
        testingService.setTestingItems(testingItemList);
        Assertions.assertThat(testingService.getTestingItems()).isEqualTo(testingItemList);
    }

    @Test
    void testStudentMethodShouldReturnCorrectStructure() {
        List<TestingItem> testingItems = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        answers.add("a1");
        TestingItem testingItem;
        testingItem = new TestingItem("q1", answers, "4");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q2", answers, "4");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q3", answers, "7");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q4", answers, "5");
        testingItems.add(testingItem);
        testingItem = new TestingItem("q5", answers, "6");
        testingItems.add(testingItem);
        TestingServiceImpl testingService = new TestingServiceImpl(testingItems, 3);

        String myInputData = "Roman,Kemaykin,4,4,5,5,6";
        Scanner inputSource = new Scanner(myInputData);
        inputSource.useDelimiter(",");
        StudentTest studentTest = testingService.testStudent(inputSource);
        Assertions.assertThat(studentTest.getName()).isEqualTo("Roman");
        Assertions.assertThat(studentTest.getLastName()).isEqualTo("Kemaykin");
        Map<Integer, Boolean> answerResults = new HashMap<>();
        answerResults.put(0, true);
        answerResults.put(1, true);
        answerResults.put(2, false);
        answerResults.put(3, true);
        answerResults.put(4, true);
        Assertions.assertThat(studentTest.getAnswerResults()).isEqualTo(answerResults);
    }
}

