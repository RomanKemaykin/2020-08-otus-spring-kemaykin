package ru.diasoft.digitalq.subscribers;

import ru.diasoft.digitalq.dto.TeamDto;

public interface TeamDeliveredSubscribeListenerService {
    void teamDelivered(TeamDto teamDto);
}
