package com.staxter.talkingplayers.client.api;

import com.staxter.talkingplayers.client.app.ClientApplicationService;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientApplicationService service;

    @Override
    public boolean receiveMessage(MessageDto message, Channel channel) {
        return service.receiveMessage(message, channel);
    }

    @Override
    public void sendCommand(CommandDto commandDto, Channel channel) {
        service.sendCommand(commandDto, channel);
    }

}
