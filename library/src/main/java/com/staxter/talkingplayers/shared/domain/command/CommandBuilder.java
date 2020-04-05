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

    public static Optional<CommandDto> build(String command) {
        if (command == null || command.isBlank())
            return Optional.empty();

        String[] processedCommand = command.split(" ");

        switch (processedCommand[0]) {
            case RegisterCommandDto.COMMAND:
                return buildRegisterCommandMessage(processedCommand);
            case MessageCommandDto.COMMAND:
                return buildMessageCommandMessage(processedCommand);
            case ListCommandDto.COMMAND:
                return Optional.of(new ListCommandDto());
            case HelpCommandDto.COMMAND:
                return Optional.of(new HelpCommandDto());
            case CloseCommandDto.COMMAND:
                return Optional.of(new CloseCommandDto());
            default:
                return Optional.of(new UnknownCommandDto(command));
        }
    }

    private static Optional<CommandDto> buildRegisterCommandMessage(String[] command) {
        if (command.length != 2) {
            System.out.println(new ErrorMessage("Invalid Register command. Specify player name. Player name cannot contain spaces").toPrint());
            return Optional.empty();
        }
        return Optional.of(new RegisterCommandDto(command[1]));
    }

    private static Optional<CommandDto> buildMessageCommandMessage(String[] command) {
        if (command.length < 3) {
            System.out.println(new ErrorMessage("Invalid Register command. Specify player name. Player name cannot contain spaces").toPrint());
            return Optional.empty();
        }
        String receptor = command[1];
        String message = Arrays.stream(command)
                .skip(2)
                .collect(Collectors.joining(" "));
        return Optional.of(new MessageCommandDto(receptor, message));
    }

}
