package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class UnknownCommandMessage extends CommandMessage {

    private static final long serialVersionUID = 752776147L;

    public UnknownCommandMessage(String command) {
        super(command);
    }

}
