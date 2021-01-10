package ru.otus.homework15.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import ru.otus.homework15.models.WordInput;
import ru.otus.homework15.models.WordProcessed;
import ru.otus.homework15.repositories.WordProcessedRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class WordProcessingGatewayTest {

    @Autowired
    private QueueChannel numericWordsOutChanel;

    @Autowired
    private QueueChannel alphanumericWordsOutChanel;

    @Autowired
    private WordProcessingGateway wordProcessingGateway;

    @MockBean
    private WordProcessedRepository wordProcessedRepository;

    @Test
    void wordProcessingFlowWithAlphanumericWordInputWithIncorrectSymbolsShouldFilterIt() {
        given(wordProcessedRepository.save(any())).willReturn(new WordProcessed(1, "abcde", "alphanumeric"));
        List<WordInput> wordInputList = List.of(new WordInput("abcde"));
        wordProcessingGateway.process(wordInputList);
        Message<?> wordProcessedMessage = numericWordsOutChanel.receive(0);
        assertThat(wordProcessedMessage).isNull();
        Message<?> alphanumericWordProcessedMessage = alphanumericWordsOutChanel.receive(0);
        assertThat(alphanumericWordProcessedMessage).isNull();
    }

    @Test
    void wordProcessingFlowWithNumericWordInputShouldPushResultToNumericWordOutputChannel() {
        WordProcessed expectedWordProcessed = new WordProcessed(1, "12345", "numeric");
        given(wordProcessedRepository.save(any())).willReturn(expectedWordProcessed);
        List<WordInput> wordInputList = List.of(new WordInput("12345"));
        wordProcessingGateway.process(wordInputList);
        Message<?> numericWordProcessedMessage = numericWordsOutChanel.receive(0);
        assertThat(numericWordProcessedMessage.getPayload()).isEqualTo(expectedWordProcessed);
        Message<?> alphanumericWordProcessedMessage = alphanumericWordsOutChanel.receive(0);
        assertThat(alphanumericWordProcessedMessage).isNull();
    }

    @Test
    void wordProcessingFlowWithAlphanumericWordInputShouldPushResultToAlphanumericWordOutputChannel() {
        WordProcessed expectedWordProcessed = new WordProcessed(1, "cde45", "alphanumeric");
        given(wordProcessedRepository.save(any())).willReturn(expectedWordProcessed);
        List<WordInput> wordInputList = List.of(new WordInput("cde45"));
        wordProcessingGateway.process(wordInputList);
        Message<?> numericWordProcessedMessage = numericWordsOutChanel.receive(0);
        assertThat(numericWordProcessedMessage).isNull();
        Message<?> alphanumericWordProcessedMessage = alphanumericWordsOutChanel.receive(0);
        assertThat(alphanumericWordProcessedMessage.getPayload()).isEqualTo(expectedWordProcessed);
    }
}