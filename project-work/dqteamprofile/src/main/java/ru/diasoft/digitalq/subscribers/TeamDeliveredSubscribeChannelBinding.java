package ru.diasoft.digitalq.subscribers;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(TeamDeliveredSubscribeChannel.class)
public class TeamDeliveredSubscribeChannelBinding {
}
