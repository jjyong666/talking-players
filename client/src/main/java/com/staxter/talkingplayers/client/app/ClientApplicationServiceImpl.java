package com.staxter.talkingplayers.client.app;

import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.OwnMessage;
import com.staxter.talkingplayers.shared.dto.PlayerMessage;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.dto.command.MessageCommandDto;

public class ClientApplicationServiceImpl implements ClientApplicationService {

    private int sentCount;

    @Override
    public void sendCommand(CommandDto command, Channel channel) {
        if (command instanceof MessageCommandDto) {
            sentCount++;
            System.out.println(new OwnMessage(((MessageCommandDto) command).getMessage()).toPrint());
        }

        channel.sendMessage(command);
    }

    @Override
    public boolean receiveMessage(MessageDto message, Channel channel) {
        if (message.getMessage().equals(CloseCommandDto.COMMAND))
            return true;

        System.out.println(message.toPrint());

        if (message instanceof PlayerMessage) {
            var playerMessage = (PlayerMessage) message;
            String messageToSend = decorateMessage(message.getMessage());
            sendCommand(new MessageCommandDto(playerMessage.getPlayer(), messageToSend), channel);
        }
        return false;
    }

    private String decorateMessage(String message) {
        return String.format("%s %d", message, sentCount);
    }

}
