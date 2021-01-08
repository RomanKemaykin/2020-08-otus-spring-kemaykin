package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.destination.models.*;
import ru.otus.batch.repositories.GenreIdCorrespondenceTableRepository;
import ru.otus.batch.source.models.GenreSource;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class PrepareDbStepConfig {
    private final Logger logger = LoggerFactory.getLogger(PrepareDbStepConfig.class);

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step prepareDbStep(PrepareDbForJob prepareDbForJob) {
        return stepBuilderFactory
                .get("prepareDbStep")
                .tasklet(prepareDbForJob)
                .build();
    }

    @Bean
    public PrepareDbForJob prepareDbForJob(MongoTemplate mongoTemplate) {
        return new PrepareDbForJob(mongoTemplate);
    }

    @RequiredArgsConstructor
    public static class PrepareDbForJob implements Tasklet {

        private final MongoTemplate mongoTemplate;

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            mongoTemplate.dropCollection(AuthorDestination.class);
            mongoTemplate.dropCollection(GenreDestination.class);
            mongoTemplate.dropCollection(BookDestination.class);
            mongoTemplate.dropCollection(AuthorIdCorrespondenceTable.class);
            mongoTemplate.dropCollection(GenreIdCorrespondenceTable.class);
            mongoTemplate.dropCollection(BookIdCorrespondenceTable.class);
            return RepeatStatus.FINISHED;
        }
    }
}
