package com.staxter.talkingplayers.shared.dto.command;

import com.staxter.talkingplayers.shared.dto.Message;
import com.staxter.talkingplayers.shared.util.ConsoleColors;
import lombok.Getter;

@Getter
public abstract class CommandMessage extends Message {

    private static final long serialVersionUID = 752776147L;

    private String args;

    public CommandMessage(String command, String... args) {
        super(command);
        this.args = String.join(" ", args);
    }

    @Override
    public String toString() {
        return String.format("%s %s", message, args);
    }

    @Override
    public String toPrint() {
        return String.format("%s%s%s %s", ConsoleColors.YELLOW_BOLD, message, ConsoleColors.RESET, args);
    }
}
