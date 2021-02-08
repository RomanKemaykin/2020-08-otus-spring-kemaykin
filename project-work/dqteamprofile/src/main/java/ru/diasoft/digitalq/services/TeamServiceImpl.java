package ru.diasoft.digitalq.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.models.TeamEntity;
import ru.diasoft.digitalq.models.TeamMemberEntity;
import ru.diasoft.digitalq.repositories.TeamMemberRepository;
import ru.diasoft.digitalq.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Page<TeamDto> findTeams(Pageable pageable) {
        Page<TeamEntity> teamEntityPage = teamRepository.findAll(pageable);
        List<TeamDto> teamDtoList = teamEntityPage.getContent().stream()
                .map(teamEntity -> teamEntityToDto(teamEntity)).collect(Collectors.toList());
        return new PageImpl<>(teamDtoList, pageable, teamEntityPage.getTotalElements());
    }

    @Override
    public Optional<TeamDto> findTeamById(long id) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(id);
        return teamEntity.map(teamEntity1 -> teamEntityToDto(teamEntity1));
    }

    @Override
    public void addTeam(TeamDto teamDto) {
        TeamEntity teamEntity = teamDtoToEntity(teamDto, null);
        teamRepository.save(teamEntity);
    }

    @Override
    public void modifyTeam(TeamDto teamDto) {
        Optional<TeamEntity> teamEntityExisting = teamRepository.findById(teamDto.getId());
        if (!teamEntityExisting.isEmpty()) {
            TeamEntity teamEntity = teamDtoToEntity(teamDto, teamEntityExisting.get());
            teamRepository.save(teamEntity);
        }
    }

    @Transactional
    @Override
    public void deleteTeam(long id) {
        Optional<TeamEntity> teamEntityOptional = teamRepository.findById(id);
        if (!teamEntityOptional.isEmpty()) {
            TeamEntity teamEntity = teamEntityOptional.get();
            teamMemberRepository.deleteTeamMemberEntitiesByTeamEntity(teamEntity);
            teamRepository.deleteById(id);
        }
    }

    private static TeamDto teamEntityToDto(TeamEntity teamEntity) {
        return TeamDto.builder()
                .id(teamEntity.getId())
                .name(teamEntity.getName())
                .teamLeadName(teamEntity.getTeamLeadName())
                .productOwnerName(teamEntity.getProductOwnerName())
                .productCenterName(teamEntity.getProductCenterName())
                .curatorName(teamEntity.getCuratorName())
                .dateStart(teamEntity.getDateStart())
                .dateEnd(teamEntity.getDateEnd())
                .build();
    }

    private static TeamEntity teamDtoToEntity(TeamDto teamDto, TeamEntity teamEntityExisting) {
        TeamEntity teamEntity = TeamEntity.builder()
                .id(teamDto.getId())
                .name(teamDto.getName())
                .teamLeadName(teamDto.getTeamLeadName())
                .productOwnerName(teamDto.getProductOwnerName())
                .productCenterName(teamDto.getProductCenterName())
                .curatorName(teamDto.getCuratorName())
                .dateStart(teamDto.getDateStart())
                .dateEnd(teamDto.getDateEnd())
                .build();

        if (teamEntityExisting != null) {
            teamEntity.setTeamMemberEntityList(teamEntityExisting.getTeamMemberEntityList());
        }
        return teamEntity;
    }

}
