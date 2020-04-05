package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class CloseCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final CloseCommandDto COMMAND = new CloseCommandDto();

    @Mock
    private ServerController controller;

    private CloseCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new CloseCommand();
    }

    @Test
    void execute_callsReceiveMessage() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

    @Test
    void execute_callsDeletePlayer() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).deletePlayer(PLAYER);
    }

    @Test
    void requiresRegistration() {

        boolean result = command.requiresRegistration();

        assertFalse(result);
    }

    @Test
    void endsCommunication() {

        boolean result = command.endsCommunication();

        assertTrue(result);
    }
}
