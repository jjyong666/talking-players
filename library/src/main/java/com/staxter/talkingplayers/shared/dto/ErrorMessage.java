package com.staxter.talkingplayers.shared.dto;

import com.staxter.talkingplayers.shared.util.ConsoleColors;

public class ErrorMessage extends Message {

    private static final long serialVersionUID = 752776147L;

    public ErrorMessage(String message) {
        super(String.format("ERROR: %s",  message));
    }

    @Override
    public String toPrint() {
        return String.format("%s%s%s", ConsoleColors.RED_BOLD, message, ConsoleColors.RESET);
    }
}
