package com.staxter.talkingplayers.shared.dto;

import com.staxter.talkingplayers.shared.util.ConsoleColors;
import lombok.Getter;

@Getter
public class PlayerMessage extends Message {

    private static final long serialVersionUID = 752776147L;

    private String player;

    public PlayerMessage(String message, String player) {
        super(message);
        this.player = player;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", player, message);
    }

    @Override
    public String toPrint() {
        return String.format("%s%s:%s %s", ConsoleColors.BLUE_BOLD, player, ConsoleColors.RESET, message);
    }
}
