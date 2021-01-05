package ru.otus.batch.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class YamlProps {

    private String jobName;
    private int chunkSize;
}
