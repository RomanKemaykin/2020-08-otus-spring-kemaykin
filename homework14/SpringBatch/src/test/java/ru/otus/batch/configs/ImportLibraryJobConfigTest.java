package ru.otus.batch.configs;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.destination.models.BookDestination;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
class ImportLibraryJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void importJobShouldCompleteSuccessfullyAndConvertExpectedCountOfBooks() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<BookDestination> bookDestinationList = mongoTemplate.findAll(BookDestination.class);
        assertThat(bookDestinationList)
                .hasSize(4);
    }
}