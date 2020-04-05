package com.staxter.talkingplayers.server.infrastructure.command.dto;

import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import lombok.Getter;

@Getter
public class ServerCommandDto extends CommandDto {

    private static final long serialVersionUID = 752776147L;

    private MessageDto message;

    public ServerCommandDto(String message) {
        super(message);
        this.message = new ServerMessage(message);
    }

}
