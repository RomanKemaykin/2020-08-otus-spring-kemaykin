package ru.otus.batch.configs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.otus.batch.destination.models.*;
import ru.otus.batch.repositories.AuthorIdCorrespondenceTableRepository;
import ru.otus.batch.repositories.GenreIdCorrespondenceTableRepository;
import ru.otus.batch.source.models.BookSource;
import ru.otus.batch.source.models.GenreSource;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class ImportBooksStepConfig {
    private final Logger logger = LoggerFactory.getLogger(ImportBooksStepConfig.class);

    private final YamlProps props;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final AuthorIdCorrespondenceTableRepository authorIdCorrespondenceTableRepository;
    private final GenreIdCorrespondenceTableRepository genreIdCorrespondenceTableRepository;

    @Bean
    public JpaPagingItemReader<BookSource> bookReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<BookSource>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from BookSource a")
                .build();
    }

    @Bean
    public ItemProcessor<BookSource, BookDestination> bookProcessor() {
        return bookSource -> bookConvertor(bookSource);
    }

    @Bean
    public MongoItemWriter<BookDestination> bookItemWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookDestination>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step importBooksStep(ItemWriter<BookDestination> writer, ItemReader<BookSource> reader, ItemProcessor<BookSource, BookDestination> itemProcessor) {
        return stepBuilderFactory
                .get("importBooksStep")
                .<BookSource, BookDestination>chunk(props.getChunkSize())
                .processor(itemProcessor)
                .reader(reader)
                .writer(writer)
                .build();
    }

    private BookDestination bookConvertor(BookSource bookSource) {
        BookIdCorrespondenceTable bookIdCorrespondenceTable = new BookIdCorrespondenceTable(null, bookSource.getId());
        mongoTemplate.save(bookIdCorrespondenceTable);
        String authorDestinationId = authorIdCorrespondenceTableRepository.findBySourceId(bookSource.getAuthor().getId()).getDestinationId();
        String genreDestinationId = genreIdCorrespondenceTableRepository.findBySourceId(bookSource.getGenre().getId()).getDestinationId();
        return new BookDestination(
                bookIdCorrespondenceTable.getDestinationId(),
                bookSource.getTitle(),
                new AuthorDestination(authorDestinationId, bookSource.getAuthor().getName()),
                new GenreDestination(genreDestinationId, bookSource.getGenre().getName())
        );
    }
}
