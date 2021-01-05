package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Step;
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
import ru.otus.batch.destination.models.AuthorDestination;
import ru.otus.batch.destination.models.AuthorIdCorrespondenceTable;
import ru.otus.batch.destination.models.GenreDestination;
import ru.otus.batch.destination.models.GenreIdCorrespondenceTable;
import ru.otus.batch.repositories.AuthorIdCorrespondenceTableRepository;
import ru.otus.batch.repositories.GenreIdCorrespondenceTableRepository;
import ru.otus.batch.source.models.AuthorSource;
import ru.otus.batch.source.models.GenreSource;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ImportGenresStepConfig {
    private final Logger logger = LoggerFactory.getLogger(ImportGenresStepConfig.class);

    private final YamlProps props;
    private final StepBuilderFactory stepBuilderFactory;
    private final GenreIdCorrespondenceTableRepository genreIdCorrespondenceTableRepository;

    @Bean
    public JpaPagingItemReader<GenreSource> genreReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<GenreSource>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from GenreSource a")
                .build();
    }

    @Bean
    public ItemProcessor<GenreSource, GenreDestination> genreProcessor() {
        return genreSource -> genreConvertor(genreSource);
    }

    @Bean
    public MongoItemWriter<GenreDestination> genreItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreDestination>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step importGenresStep(ItemWriter<GenreDestination> writer, ItemReader<GenreSource> reader, ItemProcessor<GenreSource, GenreDestination> itemProcessor) {
        return stepBuilderFactory
                .get("importGenresStep")
                .<GenreSource, GenreDestination>chunk(props.getChunkSize())
                .processor(itemProcessor)
                .reader(reader)
                .writer(writer)
                .build();
    }

    private GenreDestination genreConvertor(GenreSource genreSource) {
        GenreIdCorrespondenceTable genreIdCorrespondenceTable = new GenreIdCorrespondenceTable(null, genreSource.getId());
        genreIdCorrespondenceTableRepository.save(genreIdCorrespondenceTable);
        return new GenreDestination(genreIdCorrespondenceTable.getDestinationId(), genreSource.getName());
    }
}
