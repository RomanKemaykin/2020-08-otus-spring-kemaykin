package ru.diasoft.digitalq.subscribers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.models.TeamEntity;
import ru.diasoft.digitalq.repositories.TeamRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamDeliveredSubscribeListenerServiceImpl implements TeamDeliveredSubscribeListenerService {
    private final TeamRepository teamRepository;

    @Override
    public void teamDelivered(TeamDto teamDto) {
        TeamEntity teamEntity = null;
        Long teamId = teamDto.getId();
        boolean validTeamData = true;
        TeamEntity existingTeamEntity = null;

        if ((teamId != null) && (teamId > 0)) {
            if (teamRepository.existsById(teamId)) {
                existingTeamEntity = teamRepository.findById(teamId).get();
            } else {
                validTeamData = false;
            }
        };

        if (validTeamData) {
            teamEntity = new TeamEntity(0,
                    teamDto.getName(),
                    teamDto.getTeamLeadName(),
                    teamDto.getProductOwnerName(),
                    teamDto.getProductCenterName(),
                    teamDto.getCuratorName(),
                    null,
                    teamDto.getDateStart(),
                    teamDto.getDateEnd());

            if (existingTeamEntity != null) {
                teamEntity.setId(teamId);
                teamEntity.setTeamMemberEntityList(existingTeamEntity.getTeamMemberEntityList());
            }
            teamRepository.save(teamEntity);
        }
    }
}
