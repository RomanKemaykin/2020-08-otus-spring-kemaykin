package ru.otus.homework15.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.otus.homework15.configs.YamlProps;
import ru.otus.homework15.models.WordInput;

@RequiredArgsConstructor
@Service
public class WordFilter {
    private final YamlProps yamlProps;
    private final Logger logger = LoggerFactory.getLogger(WordFilter.class);

    public boolean meetsTheConditions(Message<WordInput> wordInputMessage) {
        String incorrectCharSequence = yamlProps.getIncorrectCharSequence();
        boolean meetsTheConditions = !(wordInputMessage.getPayload().getContent().contains(incorrectCharSequence));
        if (!meetsTheConditions) {
            logger.info("Message has been filtered (contains '" + incorrectCharSequence + "'): " + wordInputMessage);
        }
        return meetsTheConditions;
    }
}
