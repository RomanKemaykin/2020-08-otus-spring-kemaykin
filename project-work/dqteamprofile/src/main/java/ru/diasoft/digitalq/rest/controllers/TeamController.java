package ru.diasoft.digitalq.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.services.TeamService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Api(tags = {"Team"})
public class TeamController {
    private final TeamService teamService;

    @GetMapping("api/team")
    @ApiOperation(value = "Метод возвращает данные по всем командам", tags = {"Team"})
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teamDtoList = teamService.findTeams();
        return ResponseEntity.ok(teamDtoList);
    }

    @ApiOperation(value = "Метод возвращает данные по одной команде с переданным id", tags = {"Team"})
    @GetMapping("/api/team/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable long id) {
        return ResponseEntity.of(teamService.findTeamById(id));
    }

    @ApiOperation(value = "Метод добавляет новую команду", tags = {"Team"})
    @PostMapping("/api/team")
    public ResponseEntity<Void> addTeam(UriComponentsBuilder builder, @RequestBody @Validated TeamDto teamDto) {
        teamService.addTeam(teamDto);
        UriComponents uriComponents =
                builder.path("/api/team/{id}").buildAndExpand(teamDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ApiOperation(value = "Метод модифицирует данные заданной команды по id", tags = {"Team"})
    @PutMapping("/api/team/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable long id, @RequestBody TeamDto teamDto) {
        teamDto.setId(id);
        teamService.modifyTeam(teamDto);
        return ResponseEntity.ok(teamDto);
    }

    @ApiOperation(value = "Метод удаляет данные заданной команды по id", tags = {"Team"})
    @DeleteMapping("/api/team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable long id) {
        Optional<TeamDto> teamDto = teamService.findTeamById(id);
        if (!teamDto.isEmpty()) {
            teamService.deleteTeam(id);
        }
        return ResponseEntity.noContent().build();
    }
}
