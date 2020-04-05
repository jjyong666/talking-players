package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;

public class CloseCommand implements Command {

    @Override
    public void execute(ServerController controller, Player player, CommandDto command) {
        controller.receiveMessage(player, new ServerMessage("Bye."));
        controller.deletePlayer(player);
    }

    @Override
    public boolean endsCommunication() {
        return true;
    }

}
