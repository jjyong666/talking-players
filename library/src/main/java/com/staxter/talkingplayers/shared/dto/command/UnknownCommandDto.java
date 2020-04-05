package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class UnknownCommandDto extends CommandDto {

    private static final long serialVersionUID = 752776147L;

    public UnknownCommandDto(String command) {
        super(command);
    }

}
