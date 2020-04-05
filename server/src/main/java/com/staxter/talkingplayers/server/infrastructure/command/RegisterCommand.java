package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.dto.command.RegisterCommandDto;

public class RegisterCommand implements Command {

    @Override
    public void execute(ServerController controller, Player player, CommandDto command) {
        controller.registerPlayer(player, ((RegisterCommandDto) command).getName());
    }

}
