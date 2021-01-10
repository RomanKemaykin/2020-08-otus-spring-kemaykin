package ru.otus.homework15.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.homework15.models.WordInput;
import ru.otus.homework15.models.WordProcessed;

import java.util.regex.Pattern;

@Service
public class WordTransformer {
    private final Logger logger = LoggerFactory.getLogger(WordTransformer.class);

    public WordProcessed transform(WordInput wordInput) {
        String wordInputType;
        if (Pattern.matches("[a-z]+", wordInput.getContent())) {
            wordInputType = "alphabetical";
        } else if (Pattern.matches("[0-9]+", wordInput.getContent())) {
            wordInputType = "numeric";
        }
        else
            wordInputType = "alphanumeric";
        WordProcessed wordProcessed = new WordProcessed(0, wordInput.getContent(), wordInputType);
        logger.info(wordProcessed.toString());
        return wordProcessed;
    }
}
