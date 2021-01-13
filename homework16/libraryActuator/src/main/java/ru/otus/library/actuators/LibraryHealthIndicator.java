package ru.otus.library.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class LibraryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        double randomValue = ThreadLocalRandom.current().nextDouble();
        boolean libraryIsUp = randomValue < 0.9;
        if (libraryIsUp) {
            return Health.up().withDetail("randomValue", randomValue).build();
        } else {
            return Health.down().status(Status.DOWN).withDetail("randomValue", randomValue).withDetail("message", "Something went wrong!").build();
        }
    }
}
