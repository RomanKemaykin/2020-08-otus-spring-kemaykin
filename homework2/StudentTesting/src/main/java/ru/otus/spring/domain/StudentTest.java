package ru.otus.spring.domain;

import java.util.Map;

public final class StudentTest {
    private final String name;
    private final String lastName;
    private final Map<Integer, Boolean> answerResults;
    private final Boolean testPassed;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<Integer, Boolean> getAnswerResults() {
        return answerResults;
    }

    public Boolean getTestPassed() {
        return testPassed;
    }

    public Integer getCorrectAnswersNumberForPass() {
        return correctAnswersNumberForPass;
    }

    public StudentTest(String name, String lastName, Map<Integer, Boolean> answerResults, Integer correctAnswersNumberForPass) {
        this.name = name;
        this.lastName = lastName;
        this.answerResults = answerResults;
        this.correctAnswersNumberForPass = correctAnswersNumberForPass;
        this.testPassed = isTestPassed();
    }

    private Integer correctAnswersNumberForPass;

    private Boolean isTestPassed() {
        Integer correctAnswersNumber = 0;
        for (int i = 0; i < answerResults.size(); i++) {
            if (answerResults.get(i) == true) {
                correctAnswersNumber++;
            }
        }
        return (correctAnswersNumber >= correctAnswersNumberForPass);
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
