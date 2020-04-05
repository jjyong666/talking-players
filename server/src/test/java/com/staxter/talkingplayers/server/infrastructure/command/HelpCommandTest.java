package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class HelpCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final HelpCommandDto COMMAND = new HelpCommandDto();

    @Mock
    private ServerController controller;

    private HelpCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new HelpCommand();
    }

    @Test
    void execute_callsReceiveMessage() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

    @Test
    void execute_sendsCommandsDescriptions() {

        command.execute(controller, PLAYER, COMMAND);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(any(), captor.capture());
        var message = captor.getValue().getMessage();

        assertAll(
                () -> assertTrue(message.contains(RegisterCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(MessageCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(ListCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(HelpCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(CloseCommandDto.DESCRIPTION))
        );
    }

    @Test
    void requiresRegistration() {

        boolean result = command.requiresRegistration();

        assertFalse(result);
    }

    @Test
    void endsCommunication() {

        boolean result = command.endsCommunication();

        assertFalse(result);
    }
}
