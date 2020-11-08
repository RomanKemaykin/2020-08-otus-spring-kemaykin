package ru.otus.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.library.configs.YamlProps;

import java.io.IOException;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class LibraryApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
