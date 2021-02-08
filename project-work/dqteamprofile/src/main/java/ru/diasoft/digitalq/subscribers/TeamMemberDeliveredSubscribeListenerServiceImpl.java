package ru.diasoft.digitalq.subscribers;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.dto.TeamMemberDto;
import ru.diasoft.digitalq.models.TeamEntity;
import ru.diasoft.digitalq.models.TeamMemberEntity;
import ru.diasoft.digitalq.models.TeamRoleEntity;
import ru.diasoft.digitalq.repositories.TeamMemberRepository;
import ru.diasoft.digitalq.repositories.TeamRepository;
import ru.diasoft.digitalq.repositories.TeamRoleRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamMemberDeliveredSubscribeListenerServiceImpl implements TeamMemberDeliveredSubscribeListenerService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;

    @Override
    public void teamMemberDelivered(TeamMemberDto teamMemberDto) {
        TeamMemberEntity teamMemberEntity = null;
        Long teamMemberId = teamMemberDto.getId();
        boolean validTeamMemberData = true;
        TeamMemberEntity existingTeamMemberEntity = null;

        if ((teamMemberId != null) && (teamMemberId > 0)) {
            if (teamMemberRepository.existsById(teamMemberId)) {
                existingTeamMemberEntity = teamMemberRepository.findById(teamMemberId).get();
            } else {
                validTeamMemberData = false;
                logger.info("team member id is invalid");
            }
        };

        Long teamId = teamMemberDto.getTeamId();
        TeamEntity teamEntity = null;
        if (validTeamMemberData) {
            if ((teamId != null) && (teamId > 0)) {
                if (!teamRepository.existsById(teamId)) {
                    validTeamMemberData = false;
                    logger.info("team id is invalid");
                } else {
                    teamEntity = teamRepository.findById(teamId).get();
                }
            } else {
                String teamName = teamMemberDto.getTeamName();
                if ((teamName != null) && (!teamName.isEmpty())) {
                    Optional<TeamEntity> optionalTeamEntity = teamRepository.findByName(teamName);
                    if (optionalTeamEntity.isEmpty()) {
                        validTeamMemberData = false;
                        logger.info("team name is invalid");
                    } else {
                        teamEntity = optionalTeamEntity.get();
                    }
                }
            }
        }

        TeamRoleEntity teamRoleEntity = null;
        if (validTeamMemberData) {
            if (!teamMemberDto.getTeamRoleName().isEmpty()) {
                Optional<TeamRoleEntity> teamRoleEntityOptional = teamRoleRepository.findByName(teamMemberDto.getTeamRoleName());
                if (teamRoleEntityOptional.isEmpty()) {
                    teamRoleEntity = teamRoleRepository.save(new TeamRoleEntity(0, teamMemberDto.getTeamRoleName()));
                } else {
                    teamRoleEntity = teamRoleEntityOptional.get();
                }
            } else {
                validTeamMemberData = false;
                logger.info("team role name is invalid");
            }
        }

        if (validTeamMemberData) {
            teamMemberEntity = TeamMemberEntity.builder()
                    .id(teamMemberDto.getId())
                    .name(teamMemberDto.getName())
                    .percentageOfParticipation(teamMemberDto.getPercentageOfParticipation())
                    .dateStart(teamMemberDto.getDateStart())
                    .dateEnd(teamMemberDto.getDateEnd())
                    .teamEntity(teamEntity)
                    .teamRoleEntity(teamRoleEntity)
                    .build();

            teamMemberRepository.save(teamMemberEntity);
        }
    }
}
