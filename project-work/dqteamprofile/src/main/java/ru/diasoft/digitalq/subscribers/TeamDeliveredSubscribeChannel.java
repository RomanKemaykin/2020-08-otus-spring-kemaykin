package ru.diasoft.digitalq.subscribers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TeamDeliveredSubscribeChannel {
    String TEAM_DELIVERED = "teamDeliveredSubscribe";

    @Input(TEAM_DELIVERED)
    SubscribableChannel teamDelivered();
}
