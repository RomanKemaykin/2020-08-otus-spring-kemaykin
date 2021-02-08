package ru.diasoft.digitalq.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.dto.TeamMemberDto;
import ru.diasoft.digitalq.services.TeamMemberService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Api(tags = {"TeamMember"})
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @GetMapping("api/team-member")
    @ApiOperation(value = "Метод возвращает данные по всем участникам команд, по странично", tags = {"TeamMember"})
    public ResponseEntity<Page<TeamMemberDto>> getPageOfTeamMembers(Pageable pageable) {
        Page<TeamMemberDto> teamMemberDtoPage = teamMemberService.findTeamMembers(pageable);
        return ResponseEntity.ok(teamMemberDtoPage);
    }

    @ApiOperation(value = "Метод возвращает данные по всем участникам команды с переданным id", tags = {"TeamMember"})
    @GetMapping("/api/team/{id}/team-member")
    public ResponseEntity<List<TeamMemberDto>> getTeamMembersByTeam(@PathVariable long id) {
        return ResponseEntity.ok(teamMemberService.findTeamMembersByTeamId(id));
    }

    @ApiOperation(value = "Метод возвращает данные по участнику команды с переданным id", tags = {"TeamMember"})
    @GetMapping("/api/team-member/{id}")
    public ResponseEntity<TeamMemberDto> getTeamMember(@PathVariable long id) {
        return ResponseEntity.of(teamMemberService.findTeamMemberById(id));
    }

    @ApiOperation(value = "Метод добавляет нового участника команды", tags = {"TeamMember"})
    @PostMapping("/api/team-member")
    public ResponseEntity<Void> addTeamMember(UriComponentsBuilder builder, @RequestBody @Validated TeamMemberDto teamMemberDto) {
        teamMemberService.addTeamMember(teamMemberDto);
        UriComponents uriComponents =
                builder.path("/api/team-member/{id}").buildAndExpand(teamMemberDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ApiOperation(value = "Метод модифицирует данные заданного участника команды по id", tags = {"TeamMember"})
    @PutMapping("/api/team-member/{id}")
    public ResponseEntity<TeamMemberDto> updateTeamMember(@PathVariable long id, @RequestBody TeamMemberDto teamMemberDto) {
        teamMemberDto.setId(id);
        teamMemberService.modifyTeamMember(teamMemberDto);
        return ResponseEntity.ok(teamMemberDto);
    }

    @ApiOperation(value = "Метод удаляет данные заданной участника команды по id", tags = {"TeamMember"})
    @DeleteMapping("/api/team-member/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable long id) {
        Optional<TeamMemberDto> teamMemberDto = teamMemberService.findTeamMemberById(id);
        if (!teamMemberDto.isEmpty()) {
            teamMemberService.deleteTeamMember(id);
        }
        return ResponseEntity.noContent().build();
    }
}
