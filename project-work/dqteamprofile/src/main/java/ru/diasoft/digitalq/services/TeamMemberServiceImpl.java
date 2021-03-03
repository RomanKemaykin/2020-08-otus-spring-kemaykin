package ru.diasoft.digitalq.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.dto.TeamMemberDto;
import ru.diasoft.digitalq.models.TeamEntity;
import ru.diasoft.digitalq.models.TeamMemberEntity;
import ru.diasoft.digitalq.models.TeamRoleEntity;
import ru.diasoft.digitalq.repositories.TeamMemberRepository;
import ru.diasoft.digitalq.repositories.TeamRepository;
import ru.diasoft.digitalq.repositories.TeamRoleRepository;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final TeamRoleRepository teamRoleRepository;

    @Override
    public Page<TeamMemberDto> findTeamMembers(Pageable pageable) {
        Page<TeamMemberEntity> teamMemberEntityPage = teamMemberRepository.findAll(pageable);
        List<TeamMemberDto> teamMemberDtoList = teamMemberEntityPage.getContent().stream()
                .map(teamMemberEntity -> teamMemberEntityToDto(teamMemberEntity)).collect(Collectors.toList());
        return new PageImpl<>(teamMemberDtoList, pageable, teamMemberEntityPage.getTotalElements());
    }

    @HystrixCommand(commandKey = "teamMemberKey", fallbackMethod="buildFallbackTeamMembersByTeam")
    @Override
    @Transactional
    public List<TeamMemberDto> findTeamMembersByTeamId(long id) {
        if ((id % 3) == 0) {
            System.out.println("It is a chance to demonstrate Hystrix action");
            try {
                System.out.println("Start sleeping...." + System.currentTimeMillis());
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                System.out.println("Hystrix thread interrupted...." + System.currentTimeMillis());
                e.printStackTrace();
            }
        }
        List<TeamMemberDto> teamMemberDtoList = new ArrayList<>();
        Optional<TeamEntity> teamEntity = teamRepository.findById(id);
        if (!teamEntity.isEmpty()) {
            List<TeamMemberEntity> teamMemberEntityList = teamMemberRepository.findByTeamEntity(teamEntity.get());
            teamMemberDtoList = teamMemberEntityList.stream()
                    .map(teamMemberEntity -> teamMemberEntityToDto(teamMemberEntity))
                    .collect(Collectors.toList());
        }
        return teamMemberDtoList;
    }

    @Override
    public Optional<TeamMemberDto> findTeamMemberById(long id) {
        Optional<TeamMemberEntity> teamMemberEntity = teamMemberRepository.findById(id);
        return teamMemberEntity.map(teamMemberEntity1 -> teamMemberEntityToDto(teamMemberEntity1));
    }

    @Transactional
    @Override
    public void addTeamMember(TeamMemberDto teamMemberDto) {
        saveTeamMember(teamMemberDto);
    }

    @Transactional
    @Override
    public void modifyTeamMember(TeamMemberDto teamMemberDto) {
        saveTeamMember(teamMemberDto);
    }

    @Transactional
    @Override
    public void deleteTeamMember(long id) {
        if (teamMemberRepository.existsById(id)) {
            teamMemberRepository.deleteById(id);
        }
    }

    private static TeamMemberDto teamMemberEntityToDto(TeamMemberEntity teamMemberEntity) {
        return TeamMemberDto.builder()
                .id(teamMemberEntity.getId())
                .name(teamMemberEntity.getName())
                .teamId(teamMemberEntity.getTeamEntity().getId())
                .teamName(teamMemberEntity.getTeamEntity().getName())
                .teamRoleName(teamMemberEntity.getTeamRoleEntity().getName())
                .percentageOfParticipation(teamMemberEntity.getPercentageOfParticipation())
                .dateStart(teamMemberEntity.getDateStart())
                .dateEnd(teamMemberEntity.getDateEnd())
                .build();
    }

    private void saveTeamMember(TeamMemberDto teamMemberDto) {
        TeamMemberEntity teamMemberEntity = TeamMemberEntity.builder()
                .id(teamMemberDto.getId())
                .name(teamMemberDto.getName())
                .percentageOfParticipation(teamMemberDto.getPercentageOfParticipation())
                .dateStart(teamMemberDto.getDateStart())
                .dateEnd(teamMemberDto.getDateEnd())
                .build();

        Optional<TeamEntity> teamEntity = teamRepository.findById(teamMemberDto.getTeamId());

        if (!teamEntity.isEmpty()) {
            teamMemberEntity.setTeamEntity(teamEntity.get());

            TeamRoleEntity teamRoleEntity;
            Optional<TeamRoleEntity> teamRoleEntityOptional = teamRoleRepository.findByName(teamMemberDto.getTeamRoleName());
            if (teamRoleEntityOptional.isEmpty()) {
                teamRoleEntity = teamRoleRepository.save(new TeamRoleEntity(0, teamMemberDto.getTeamRoleName()));
            } else {
                teamRoleEntity = teamRoleEntityOptional.get();
            }
            teamMemberEntity.setTeamRoleEntity(teamRoleEntity);

            teamMemberRepository.save(teamMemberEntity);
        }
    }

    private List<TeamMemberDto> buildFallbackTeamMembersByTeam(long id) {
        System.out.println("buildFallbackTeamMembersByTeam");
        TeamMemberDto teamMemberDto = TeamMemberDto.builder()
                .id(0)
                .teamId(id)
                .teamName("-")
                .teamRoleName("-")
                .name("-")
                .percentageOfParticipation(0)
                .dateEnd(LocalDate.now())
                .dateStart(LocalDate.now())
                .build();
        List<TeamMemberDto> teamMemberDtoList = new ArrayList<>();
        teamMemberDtoList.add(teamMemberDto);
        return teamMemberDtoList;
    }
}
