package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.batch.destination.models.*;
import ru.otus.batch.source.models.GenreSource;

@RequiredArgsConstructor
@Configuration
public class ImportLibraryJobConfig {
    private final Logger logger = LoggerFactory.getLogger(ImportLibraryJobConfig.class);

    private final YamlProps props;
    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job importJob(Step importAuthorsStep,
                         Step importGenresStep,
                         Step importBooksStep,
                         Step prepareDbStep,
                         Step removeUnnecessaryStep) {
        return jobBuilderFactory.get(props.getJobName())
                .incrementer(new RunIdIncrementer())
                .flow(prepareDbStep)
                .next(importAuthorsStep)
                .next(importGenresStep)
                .next(importBooksStep)
                .next(removeUnnecessaryStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.debug("Начало " + props.getJobName());
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.debug("Конец " + props.getJobName());
                    }
                })
                .build();
    }
}
