package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.library.configs.YamlProps;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class LibraryApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
