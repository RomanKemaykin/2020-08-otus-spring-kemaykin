package ru.diasoft.digitalq.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(value = "TeamMemberDto", description = "Dto для данных по участнику команды")
@Builder
@AllArgsConstructor
@Data
public class TeamMemberDto {
    private long id;

    private String name;

    private String teamRoleName;

    private long teamId;

    private String teamName;

    private int percentageOfParticipation;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}
