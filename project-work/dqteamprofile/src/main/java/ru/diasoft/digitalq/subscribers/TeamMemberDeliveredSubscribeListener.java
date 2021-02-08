package ru.diasoft.digitalq.subscribers;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import ru.diasoft.digitalq.dto.TeamDto;
import ru.diasoft.digitalq.dto.TeamMemberDto;

@RequiredArgsConstructor
@Configuration
public class TeamMemberDeliveredSubscribeListener {
    private final TeamMemberDeliveredSubscribeListenerService teamMemberDeliveredSubscribeListenerService;

    @StreamListener(TeamMemberDeliveredSubscribeChannel.TEAM_MEMBER_DELIVERED)
    void teamMemberDelivered(TeamMemberDto teamMemberDto) {
        teamMemberDeliveredSubscribeListenerService.teamMemberDelivered(teamMemberDto);
    }
}
