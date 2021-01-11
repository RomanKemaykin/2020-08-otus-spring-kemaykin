package ru.otus.homework15.integration;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.homework15.models.WordInput;

import java.util.List;

@Component
@MessagingGateway
public interface WordProcessingGateway {
    @Gateway(requestChannel = "wordProcessingFlow.input")
    void process(List<WordInput> wordInputList);
}
