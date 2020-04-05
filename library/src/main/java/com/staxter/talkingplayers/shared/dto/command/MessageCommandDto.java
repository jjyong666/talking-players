package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class MessageCommandDto extends CommandDto {

    public static final String COMMAND = "/msg";
    public static final String DESCRIPTION = COMMAND + " {other_player} {message} -> Message to registered player";
    private static final long serialVersionUID = 752776147L;

    private String name;
    private String message;

    public MessageCommandDto(String name, String message) {
        super(COMMAND, name, message);
        this.name = name;
        this.message = message;
    }

}
