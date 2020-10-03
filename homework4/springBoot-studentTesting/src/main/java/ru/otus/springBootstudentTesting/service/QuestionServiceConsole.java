package ru.otus.springBootstudentTesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.springBootstudentTesting.dao.TestingItemDao;
import ru.otus.springBootstudentTesting.domain.TestingItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionServiceConsole implements QuestionService {
    // Fields

    private final IOService ioService;

    private final TestingItemDao testingItemDao;

    private List<TestingItem> testingItems;

    // Methods

    @Override
    public Map<Integer, Boolean> getAnswersResults() {
        if (testingItems == null) {
            testingItems = testingItemDao.findAll();
        }
        Map<Integer, Boolean> answersResults = new HashMap<>();
        for (int i = 0; i < testingItems.size(); i++) {
            TestingItem testingItem = testingItems.get(i);
            ioService.out(testingItem.getQuestion());
            List<String> answers = testingItem.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                ioService.out(answers.get(j));
            }
            String answer = ioService.readString();
            Boolean answerResult = answer.contentEquals(testingItem.getCorrectAnswer());
            answersResults.put(i, answerResult);
        }
        return answersResults;
    }
}
