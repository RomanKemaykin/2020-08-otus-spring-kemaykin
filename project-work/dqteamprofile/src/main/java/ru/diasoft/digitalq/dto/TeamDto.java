package ru.diasoft.digitalq.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(value = "TeamDto", description = "Dto для данных по команде")
@Builder
@AllArgsConstructor
@Data
public class TeamDto {
    @ApiModelProperty(name = "id", dataType = "java.lang.long", value = "", required = true, position = 1)
    private long id;
    @ApiModelProperty(name = "name", dataType = "java.lang.String", value = "", required = true, position = 2)
    private String name;
    @ApiModelProperty(name = "teamLeadName", dataType = "java.lang.String", value = "", required = true, position = 3)
    private String teamLeadName;
    @ApiModelProperty(name = "productOwnerName", dataType = "java.lang.String", value = "", required = true, position = 4)
    private String productOwnerName;
    @ApiModelProperty(name = "productCenterName", dataType = "java.lang.String", value = "", required = true, position = 5)
    private String productCenterName;
    @ApiModelProperty(name = "curatorName", dataType = "java.lang.String", value = "", required = true, position = 6)
    private String curatorName;
    @ApiModelProperty(name = "dateStart", dataType = "java.time.LocalDate", value = "", required = true, position = 7)
    private LocalDate dateStart;
    @ApiModelProperty(name = "dateEnd", dataType = "java.time.LocalDate", value = "", required = true, position = 8)
    private LocalDate dateEnd;
}
