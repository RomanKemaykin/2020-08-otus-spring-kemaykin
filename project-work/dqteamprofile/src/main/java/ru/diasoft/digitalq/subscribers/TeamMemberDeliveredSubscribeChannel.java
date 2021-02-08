package ru.diasoft.digitalq.subscribers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TeamMemberDeliveredSubscribeChannel {
    String TEAM_MEMBER_DELIVERED = "teamMemberDeliveredSubscribe";

    @Input(TEAM_MEMBER_DELIVERED)
    SubscribableChannel teamMemberDelivered();
}
