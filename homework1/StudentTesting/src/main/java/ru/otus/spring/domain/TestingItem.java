package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class TestingItem {
    private String question;
    private List<String> answers;

    public TestingItem() {
        answers = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }


    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public List<String> getAnswers() {
        return answers;
    }
}
