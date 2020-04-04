package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class RegisterCommandMessage extends CommandMessage {

    public static final String COMMAND = "/register";
    public static final String DESCRIPTION = COMMAND + " {player_name} -> Register specifying your name";
    private static final long serialVersionUID = 752776147L;

    private String name;

    public RegisterCommandMessage(String name) {
        super(COMMAND, name);
        this.name = name;
    }

}
