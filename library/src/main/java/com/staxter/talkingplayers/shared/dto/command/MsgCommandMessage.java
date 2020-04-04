package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class MsgCommandMessage extends CommandMessage {

    public static final String COMMAND = "/msg";
    public static final String DESCRIPTION = COMMAND + " {other_player} {message} -> Message to registered player";
    private static final long serialVersionUID = 752776147L;

    private String name;
    private String messageToSend;

    public MsgCommandMessage(String name, String messageToSend) {
        super(COMMAND, name, messageToSend);
        this.name = name;
        this.messageToSend = messageToSend;
    }

}
