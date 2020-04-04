package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class CloseCommandMessage extends CommandMessage {

    public static final String COMMAND = "/close";
    public static final String DESCRIPTION = COMMAND + " -> Close your session";
    private static final long serialVersionUID = 752776147L;

    public CloseCommandMessage() {
        super(COMMAND);
    }

}
