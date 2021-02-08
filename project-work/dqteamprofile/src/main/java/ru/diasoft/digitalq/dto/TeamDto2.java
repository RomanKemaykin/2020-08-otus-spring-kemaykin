package ru.diasoft.digitalq.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(value = "TeamDto2", description = "Dto для данных по команде")
@Builder
@AllArgsConstructor
@Data
public class TeamDto2 {
    @ApiModelProperty(name = "id", dataType = "java.lang.long", value = "", required = true, position = 1)
    private long id;
    @ApiModelProperty(name = "name", dataType = "java.lang.String", value = "", required = true, position = 2)
    private String name;
}
