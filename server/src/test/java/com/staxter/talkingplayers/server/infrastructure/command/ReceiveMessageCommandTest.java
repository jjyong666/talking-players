package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ReceiveMessageCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final ServerCommandDto COMMAND = new ServerCommandDto("asasas");

    @Mock
    private ServerController controller;

    private ReceiveMessageCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new ReceiveMessageCommand();
    }

    @Test
    void execute_callsReceiveMessage() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

    @Test
    void execute_showsMessage() {

        command.execute(controller, PLAYER, COMMAND);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(any(), captor.capture());
        var message = captor.getValue();

        assertEquals(COMMAND.getMessage(), message);
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
