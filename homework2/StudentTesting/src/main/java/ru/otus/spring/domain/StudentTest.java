package ru.otus.spring.domain;

import java.util.HashMap;
import java.util.Map;

public final class StudentTest {
    private final String name;
    private final String lastName;
    private Map<Integer, Boolean> answerResults;
    private final Boolean testPassed;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<Integer, Boolean> getAnswerResults() {
        Map<Integer, Boolean> answerResultsClone = new HashMap<>();
        for (int i = 0; i < answerResults.size(); i++) {
            answerResultsClone.put(i, answerResults.get(i));
        }
        return answerResultsClone;
    }

    public StudentTest(String name, String lastName, Map<Integer, Boolean> answerResults, Boolean testPassed) {
        this.name = name;
        this.lastName = lastName;
        this.answerResults = answerResults;
        this.testPassed = testPassed;
    }

    public void printTestingResults() {
        System.out.println(name + ' ' + lastName);
        if (testPassed) {
            System.out.println("congratulations you passed the test successfully");
        } else {
            System.out.println("unfortunately you have not passed the test");
        }
    }
}
