package ru.diasoft.digitalq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import ru.diasoft.digitalq.configs.YamlProps;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
@EnableCircuitBreaker
public class DevProfile {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DevProfile.class, args);
    }
}
