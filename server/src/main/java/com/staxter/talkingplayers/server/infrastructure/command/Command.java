package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;

public interface Command {

    void execute(ServerController controller, Player player, CommandDto command);

    default boolean requiresRegistration() {
        return false;
    }

    default boolean endsCommunication() {
        return false;
    }

}
