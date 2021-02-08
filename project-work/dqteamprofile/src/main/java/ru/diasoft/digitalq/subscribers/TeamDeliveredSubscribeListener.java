package ru.diasoft.digitalq.subscribers;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import ru.diasoft.digitalq.dto.TeamDto;

@RequiredArgsConstructor
@Configuration
public class TeamDeliveredSubscribeListener {
    private final TeamDeliveredSubscribeListenerService teamDeliveredSubscribeListenerService;

    @StreamListener(TeamDeliveredSubscribeChannel.TEAM_DELIVERED)
    void teamDelivered(TeamDto teamDto) {
        teamDeliveredSubscribeListenerService.teamDelivered(teamDto);
    }
}
