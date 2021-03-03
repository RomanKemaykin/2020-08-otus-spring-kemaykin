package ru.diasoft.digitalq.subscribers;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(TeamMemberDeliveredSubscribeChannel.class)
public class TeamMemberDeliveredSubscribeChannelBinding {
}
