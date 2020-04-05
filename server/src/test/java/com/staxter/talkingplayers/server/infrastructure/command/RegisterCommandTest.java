package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.command.RegisterCommandDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class RegisterCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final RegisterCommandDto COMMAND = new RegisterCommandDto("asasas");

    @Mock
    private ServerController controller;

    private RegisterCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new RegisterCommand();
    }

    @Test
    void execute_callsRegisterPlayer() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).registerPlayer(eq(PLAYER), eq(COMMAND.getName()));
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
