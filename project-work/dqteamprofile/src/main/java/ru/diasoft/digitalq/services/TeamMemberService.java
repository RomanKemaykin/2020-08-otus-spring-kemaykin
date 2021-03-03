package ru.diasoft.digitalq.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.dto.TeamMemberDto;

import java.util.List;
import java.util.Optional;

public interface TeamMemberService {
    Page<TeamMemberDto> findTeamMembers(Pageable pageable);

    List<TeamMemberDto> findTeamMembersByTeamId(long id);

    Optional<TeamMemberDto> findTeamMemberById(long id);

    void addTeamMember(TeamMemberDto teamMemberDto);

    void modifyTeamMember(TeamMemberDto teamMemberDto);

    void deleteTeamMember(long id);
}
