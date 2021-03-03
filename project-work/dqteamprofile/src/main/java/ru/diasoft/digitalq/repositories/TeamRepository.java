package ru.diasoft.digitalq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.digitalq.models.TeamEntity;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByName(String name);
}
