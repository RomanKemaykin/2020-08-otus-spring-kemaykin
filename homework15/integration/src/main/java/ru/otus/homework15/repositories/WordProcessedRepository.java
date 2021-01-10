package ru.otus.homework15.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework15.models.WordProcessed;

public interface WordProcessedRepository extends JpaRepository<WordProcessed, Long> {
}
