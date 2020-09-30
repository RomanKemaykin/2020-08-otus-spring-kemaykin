package ru.otus.springBootstudentTesting.domain;

import java.util.List;

public final class TestingItem {
    private final String question;
    private final List<String> answers;
    private final String correctAnswer;

    public TestingItem(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
