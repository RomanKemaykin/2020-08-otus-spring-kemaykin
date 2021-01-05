package ru.otus.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.batch.configs.YamlProps;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
