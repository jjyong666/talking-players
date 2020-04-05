package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.dto.command.MessageCommandDto;

public class SendMessageCommand implements Command {

    @Override
    public void execute(ServerController controller, Player player, CommandDto command) {
        var msgCommand = (MessageCommandDto) command;
        controller.sendMessage(player, msgCommand.getName(), msgCommand.getMessage());
    }

    @Override
    public boolean requiresRegistration() {
        return true;
    }

}
