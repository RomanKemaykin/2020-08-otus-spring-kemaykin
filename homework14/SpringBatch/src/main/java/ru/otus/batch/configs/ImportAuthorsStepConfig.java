package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.destination.models.*;
import ru.otus.batch.repositories.AuthorIdCorrespondenceTableRepository;
import ru.otus.batch.source.models.AuthorSource;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ImportAuthorsStepConfig {
    private final Logger logger = LoggerFactory.getLogger(ImportAuthorsStepConfig.class);

    private final YamlProps props;
    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorIdCorrespondenceTableRepository authorIdCorrespondenceTableRepository;

    @Bean
    public JpaPagingItemReader<AuthorSource> authorReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<AuthorSource>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from AuthorSource a")
                .build();
    }

    @Bean
    public ItemProcessor<AuthorSource, AuthorDestination> authorProcessor() {
        return authorSource -> authorConvertor(authorSource);
    }

    @Bean
    public MongoItemWriter<AuthorDestination> authorItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorDestination>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step importAuthorsStep(ItemWriter<AuthorDestination> writer, ItemReader<AuthorSource> reader, ItemProcessor<AuthorSource, AuthorDestination> itemProcessor) {
        return stepBuilderFactory
                .get("importAuthorsStep")
                .<AuthorSource, AuthorDestination>chunk(props.getChunkSize())
                .processor(itemProcessor)
                .reader(reader)
                .writer(writer)
                .listener(new ItemWriteListener<AuthorDestination>() {
                    public void beforeWrite(List list) {
                        logger.debug("Начало записи");
                    }

                    public void afterWrite(List list) {
                        logger.debug("Конец записи" + list);
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.debug("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<AuthorSource, AuthorDestination>() {
                    public void beforeProcess(AuthorSource o) {
                        logger.debug("Начало обработки");
                    }

                    public void afterProcess(AuthorSource authorSource, AuthorDestination authorDestination) {
                        logger.debug("Конец обработки" + authorSource + authorDestination);
                    }

                    public void onProcessError(AuthorSource o, Exception e) {
                        logger.debug("Ошбка обработки");
                    }
                })
                .build();
    }

    private AuthorDestination authorConvertor(AuthorSource authorSource) {
        AuthorIdCorrespondenceTable authorIdCorrespondenceTable = new AuthorIdCorrespondenceTable(null, authorSource.getId());
        authorIdCorrespondenceTableRepository.save(authorIdCorrespondenceTable);
        return new AuthorDestination(authorIdCorrespondenceTable.getDestinationId(), authorSource.getName());
    }
}
