package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class HelpCommandDto extends CommandDto {

    public static final String COMMAND = "/help";
    public static final String DESCRIPTION = COMMAND + " -> Prints this message";
    private static final long serialVersionUID = 752776147L;

    public HelpCommandDto() {
        super(COMMAND);
    }

}
