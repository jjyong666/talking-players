package com.staxter.talkingplayers.client.app;

import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;

public interface ClientApplicationService {

    boolean receiveMessage(MessageDto message, Channel channel);

    void sendCommand(CommandDto message, Channel channel);

}
