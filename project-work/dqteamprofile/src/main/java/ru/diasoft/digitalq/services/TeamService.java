package ru.diasoft.digitalq.services;

import ru.diasoft.digitalq.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<TeamDto> findTeams();

    Optional<TeamDto> findTeamById(long id);

    void addTeam(TeamDto teamDto);

    void modifyTeam(TeamDto teamDto);

    void deleteTeam(long id);
}
