package com.staxter.talkingplayers.client.api;

import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;

public interface ClientController {

    boolean receiveMessage(MessageDto message, Channel channel);

    void sendCommand(CommandDto command, Channel channel);

}
