package ru.otus.homework15.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class YamlProps {
    private int wordSize;
    private int wordMaxCount;
    private String incorrectCharSequence;
}
