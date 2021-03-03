package ru.diasoft.digitalq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.digitalq.models.TeamRoleEntity;

import java.util.Optional;

public interface TeamRoleRepository extends JpaRepository<TeamRoleEntity, Long> {
    Optional<TeamRoleEntity> findByName(String name);
}
