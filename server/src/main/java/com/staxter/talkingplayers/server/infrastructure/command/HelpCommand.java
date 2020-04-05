package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.*;

public class HelpCommand implements Command {

    @Override
    public void execute(ServerController controller, Player player, CommandDto command) {
        controller.receiveMessage(player, new ServerMessage(getCommands()));
    }

    private String getCommands() {
        return String.join(
                System.lineSeparator(),
                "Available commands:",
                RegisterCommandDto.DESCRIPTION,
                MessageCommandDto.DESCRIPTION,
                ListCommandDto.DESCRIPTION,
                HelpCommandDto.DESCRIPTION,
                CloseCommandDto.DESCRIPTION);
    }

}
