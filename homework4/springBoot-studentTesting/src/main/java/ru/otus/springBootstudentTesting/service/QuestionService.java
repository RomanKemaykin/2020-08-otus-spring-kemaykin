package ru.otus.springBootstudentTesting.service;

import java.util.Map;

public interface QuestionService {
    // ожидается что метод вернет мапу с результами ответов на вопросы:
    // порядковый номер вопроса, результат ответа - правильно\неправильно
    public Map<Integer, Boolean> getAnswersResults();
}
