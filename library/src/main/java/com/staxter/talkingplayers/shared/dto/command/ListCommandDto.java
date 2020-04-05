package com.staxter.talkingplayers.shared.dto.command;

import lombok.Getter;

@Getter
public class ListCommandDto extends CommandDto {

    public static final String COMMAND = "/list";
    public static final String DESCRIPTION = COMMAND + " -> Lists all players";
    private static final long serialVersionUID = 752776147L;

    public ListCommandDto() {
        super(COMMAND);
    }

}
