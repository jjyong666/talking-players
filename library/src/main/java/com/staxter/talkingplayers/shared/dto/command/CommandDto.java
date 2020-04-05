package com.staxter.talkingplayers.shared.dto.command;

import com.staxter.talkingplayers.shared.dto.Message;
import lombok.Getter;

@Getter
public abstract class CommandDto implements Message {

    private static final long serialVersionUID = 752776147L;

    private String command;
    private String args;

    public CommandDto(String command, String... args) {
        this.command = command;
        this.args = String.join(" ", args);
    }

    @Override
    public String toString() {
        return String.format("%s %s", command, args);
    }

}
