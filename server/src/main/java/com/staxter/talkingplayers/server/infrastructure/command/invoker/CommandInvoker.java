package com.staxter.talkingplayers.server.infrastructure.command.invoker;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.command.*;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.shared.dto.command.*;
import lombok.RequiredArgsConstructor;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;

@RequiredArgsConstructor
public class CommandInvoker {

    private final ServerController controller;

    private Map<Class<? extends CommandDto>, Command> commands = Map.ofEntries(
            buildEntry(RegisterCommandDto.class, new RegisterCommand()),
            buildEntry(MessageCommandDto.class, new SendMessageCommand()),
            buildEntry(ServerCommandDto.class, new ReceiveMessageCommand()),
            buildEntry(ListCommandDto.class, new ListCommand()),
            buildEntry(HelpCommandDto.class, new HelpCommand()),
            buildEntry(CloseCommandDto.class, new CloseCommand())
    );

    private SimpleImmutableEntry<Class<? extends CommandDto>, Command> buildEntry(Class<? extends CommandDto> clazz, Command command) {
        return new SimpleImmutableEntry<>(clazz, command);
    }

    public boolean processCommand(CommandDto commandDto, Player player) {
        if (commandDto == null || commandDto.getCommand() == null || commandDto.getCommand().isBlank())
            return false;

        System.out.println(String.format("%s: %s", player.getName(), commandDto));

        Command command = commands.get(commandDto.getClass());
        if (command == null) {
            sendServerMessage(player, "Invalid command! Use /help for more info.");
            return false;
        }

        executeCommand(command, commandDto, player);
        return command.endsCommunication();
    }

    private void sendServerMessage(Player player, String message) {
        commands.get(ServerCommandDto.class).execute(controller, player, new ServerCommandDto(message));
    }

    private void executeCommand(Command command, CommandDto message, Player player) {
        if (command.requiresRegistration() && !controller.existsPlayer(player.getName())) {
            sendServerMessage(player, "Please register before executing this command");
        } else {
            command.execute(controller, player, message);
        }
    }

}
