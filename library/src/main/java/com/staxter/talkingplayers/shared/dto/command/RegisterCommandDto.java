package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class RegisterCommandDto extends CommandDto {

    public static final String COMMAND = "/register";
    public static final String DESCRIPTION = COMMAND + " {player_name} -> Register specifying your name";
    private static final long serialVersionUID = 752776147L;

    private String name;

    public RegisterCommandDto(String name) {
        super(COMMAND, name);
        this.name = name;
    }

}
