package com.staxter.talkingplayers.shared.dto;

import com.staxter.talkingplayers.shared.util.ConsoleColors;

public class ServerMessage extends MessageDto {

    private static final long serialVersionUID = 752776147L;

    public ServerMessage(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("Server: %s", message);
    }

    @Override
    public String toPrint() {
        return String.format("%sServer:%s %s", ConsoleColors.RED_BOLD, ConsoleColors.RESET, message);
    }
}
