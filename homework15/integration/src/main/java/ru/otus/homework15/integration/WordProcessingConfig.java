package ru.otus.homework15.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import ru.otus.homework15.models.WordProcessed;
import ru.otus.homework15.repositories.WordProcessedRepository;
import ru.otus.homework15.services.WordFilter;
import ru.otus.homework15.services.WordProcessedRoutingFunction;
import ru.otus.homework15.services.WordTransformer;

@RequiredArgsConstructor
@IntegrationComponentScan
@Configuration
@EnableIntegration
public class WordProcessingConfig {
    private static final int DEFAULT_QUEUE_CAPACITY = 100;
    private static final int DEFAULT_POLLER_PERIOD = 1000;
    private static final String SAVE_METHOD_NAME = "save";
    private static final String TRANSFORMER_METHOD_NAME = "transform";
    private static final String FILTER_METHOD_NAME = "meetsTheConditions";

    private final WordTransformer wordTransformer;
    private final WordProcessedRepository wordProcessedRepository;
    private final WordFilter wordFilter;
    private final WordProcessedRoutingFunction wordProcessedRoutingFunction;

    @Bean
    public PollableChannel wordProcessingFlowChannel() {
        return MessageChannels.queue("wordProcessingFlow.input", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(DEFAULT_POLLER_PERIOD).get();
    }

    @Bean
    public QueueChannel numericWordsOutChanel() {
        return new QueueChannel();
    }

    @Bean
    public QueueChannel alphanumericWordsOutChanel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow wordProcessingFlow() {
        return flow -> flow
                .split()
                .filter(wordFilter, FILTER_METHOD_NAME)
                .transform(wordTransformer, TRANSFORMER_METHOD_NAME)
                .handle(wordProcessedRepository, SAVE_METHOD_NAME)
                .<WordProcessed, Boolean>route(
                        wordProcessedRoutingFunction::routingFunctionResult,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf.channel(numericWordsOutChanel()))
                                .subFlowMapping(false, sf -> sf.channel(alphanumericWordsOutChanel()))
                );
    }
}
