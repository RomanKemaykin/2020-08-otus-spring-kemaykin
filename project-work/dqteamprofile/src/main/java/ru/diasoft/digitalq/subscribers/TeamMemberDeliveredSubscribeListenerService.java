package ru.diasoft.digitalq.subscribers;

import ru.diasoft.digitalq.dto.TeamMemberDto;

public interface TeamMemberDeliveredSubscribeListenerService {
    void teamMemberDelivered(TeamMemberDto teamMemberDto);
}
