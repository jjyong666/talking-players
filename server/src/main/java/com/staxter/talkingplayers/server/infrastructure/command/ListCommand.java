package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;

import java.util.stream.Collectors;

public class ListCommand implements Command {

    @Override
    public void execute(ServerController controller, Player player, CommandDto command) {
        controller.receiveMessage(
                player,
                new ServerMessage(String.format("Players list: %n%s", getPlayers(controller))));
    }

    private String getPlayers(ServerController controller) {
        return controller.getPlayersNames()
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public boolean requiresRegistration() {
        return true;
    }

}
