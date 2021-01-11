package ru.otus.homework15.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.homework15.configs.YamlProps;
import ru.otus.homework15.integration.WordProcessingGateway;
import ru.otus.homework15.models.WordInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class WordInputEmitterService {
    private final Logger logger = LoggerFactory.getLogger(WordInputEmitterService.class);

    private final WordProcessingGateway wordProcessingGateway;
    private final YamlProps yamlProps;

    private final static char[] ALPHABET = new String("abcdefghijklmnopqrstuvwxyz0123456789").toCharArray();

    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    public void emitAppUserActivity(){
        Random random = new Random();
        int wordSize = yamlProps.getWordSize();
        int wordMaxCount = yamlProps.getWordMaxCount();
        int wordCount = random.nextInt(wordMaxCount) + 1;

        List<WordInput> wordInputList = new ArrayList<>();
        for (int j = 0; j < wordCount; j++) {
            String word = "";
            for (int i = 0; i < wordSize; i++) {
                char symbol = ALPHABET[random.nextInt(ALPHABET.length - 1)];
                word = word + symbol;
            }
           wordInputList.add(new WordInput(word));
        }

        logger.info(wordInputList.toString());
        wordProcessingGateway.process(wordInputList);
    }
}
