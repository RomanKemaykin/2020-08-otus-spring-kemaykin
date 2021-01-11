package ru.otus.homework15.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.homework15.models.WordProcessed;

@Service
public class WordProcessedRoutingFunction {
    private final Logger logger = LoggerFactory.getLogger(WordProcessedRoutingFunction.class);

    public Boolean routingFunctionResult(WordProcessed wordProcessed) {
        Boolean result = wordProcessed.getType().equals("numeric");
        logger.info("routing: " + wordProcessed + ", functionResult = " + result.toString());
        return result;
    }
}
