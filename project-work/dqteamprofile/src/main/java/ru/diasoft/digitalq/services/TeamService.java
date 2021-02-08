package ru.diasoft.digitalq.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.diasoft.digitalq.dto.TeamDto;

import java.util.Optional;

public interface TeamService {
    Page<TeamDto> findTeams(Pageable pageable);

    Optional<TeamDto> findTeamById(long id);

    void addTeam(TeamDto teamDto);

    void modifyTeam(TeamDto teamDto);

    void deleteTeam(long id);
}
