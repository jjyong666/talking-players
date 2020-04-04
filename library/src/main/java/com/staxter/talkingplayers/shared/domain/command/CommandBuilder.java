package com.staxter.talkingplayers.shared.domain.command;

import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.command.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandBuilder {

    public static Optional<CommandMessage> build(String command) {
        if (command == null || command.isBlank())
            return Optional.empty();

        String[] processedCommand = command.split(" ");

        switch (processedCommand[0]) {
            case RegisterCommandMessage.COMMAND:
                return buildRegisterCommandMessage(processedCommand);
            case MsgCommandMessage.COMMAND:
                return buildMessageCommandMessage(processedCommand);
            case ListCommandMessage.COMMAND:
                return Optional.of(new ListCommandMessage());
            case HelpCommandMessage.COMMAND:
                return Optional.of(new HelpCommandMessage());
            case CloseCommandMessage.COMMAND:
                return Optional.of(new CloseCommandMessage());
            default:
                return Optional.of(new UnknownCommandMessage(command));
        }
    }

    private static Optional<CommandMessage> buildRegisterCommandMessage(String[] command) {
        if (command.length != 2) {
            System.out.println(new ErrorMessage("Invalid Register command. Specify player name. Player name cannot contain spaces").toPrint());
            return Optional.empty();
        }
        return Optional.of(new RegisterCommandMessage(command[1]));
    }

    private static Optional<CommandMessage> buildMessageCommandMessage(String[] command) {
        if (command.length < 3) {
            System.out.println(new ErrorMessage("Invalid Register command. Specify player name. Player name cannot contain spaces").toPrint());
            return Optional.empty();
        }
        String receptor = command[1];
        String message = Arrays.stream(command)
                .skip(2)
                .collect(Collectors.joining(" "));
        return Optional.of(new MsgCommandMessage(receptor, message));
    }

}
