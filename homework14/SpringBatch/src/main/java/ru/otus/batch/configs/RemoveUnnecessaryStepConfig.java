package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.destination.models.*;

@RequiredArgsConstructor
@Configuration
public class RemoveUnnecessaryStepConfig {
    private final Logger logger = LoggerFactory.getLogger(RemoveUnnecessaryStepConfig.class);

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step removeUnnecessaryStep(RemoveUnnecessaryAfterJob removeUnnecessaryAfterJob) {
        return stepBuilderFactory
                .get("removeUnnecessaryStep")
                .tasklet(removeUnnecessaryAfterJob)
                .build();
    }

    @Bean
    public RemoveUnnecessaryAfterJob removeUnnecessary(MongoTemplate mongoTemplate) {
        return new RemoveUnnecessaryAfterJob(mongoTemplate);
    }

    @RequiredArgsConstructor
    public class RemoveUnnecessaryAfterJob implements Tasklet {

        private final MongoTemplate mongoTemplate;

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            mongoTemplate.dropCollection(AuthorIdCorrespondenceTable.class);
            mongoTemplate.dropCollection(GenreIdCorrespondenceTable.class);
            mongoTemplate.dropCollection(BookIdCorrespondenceTable.class);
            return RepeatStatus.FINISHED;
        }
    }
}
