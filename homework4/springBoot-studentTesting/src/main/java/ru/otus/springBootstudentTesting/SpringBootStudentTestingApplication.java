package ru.otus.springBootstudentTesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.service.TestingService;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class SpringBootStudentTestingApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringBootStudentTestingApplication.class, args);
    }
}
