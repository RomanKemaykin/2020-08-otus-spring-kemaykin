package ru.diasoft.digitalq.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.digitalq.models.TeamEntity;
import ru.diasoft.digitalq.models.TeamMemberEntity;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Long> {
    void deleteTeamMemberEntitiesByTeamEntity(TeamEntity teamEntity);
}
