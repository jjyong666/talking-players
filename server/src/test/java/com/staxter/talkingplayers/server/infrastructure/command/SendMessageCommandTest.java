package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.command.MessageCommandDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class SendMessageCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final MessageCommandDto COMMAND = new MessageCommandDto("assdsd", "dfdfdfdf");

    @Mock
    private ServerController controller;

    private SendMessageCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new SendMessageCommand();
    }

    @Test
    void execute_callsReceiveMessage() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).sendMessage(eq(PLAYER), eq(COMMAND.getName()), eq(COMMAND.getMessage()));
    }

    @Test
    void requiresRegistration() {

        boolean result = command.requiresRegistration();

        assertTrue(result);
    }

    @Test
    void endsCommunication() {

        boolean result = command.endsCommunication();

        assertFalse(result);
    }
}
