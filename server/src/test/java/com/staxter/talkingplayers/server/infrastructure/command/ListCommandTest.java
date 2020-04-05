package com.staxter.talkingplayers.server.infrastructure.command;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ListCommandTest {

    private static final Player PLAYER = new Player("name", null, null);
    private static final ListCommandDto COMMAND = new ListCommandDto();

    @Mock
    private ServerController controller;

    private ListCommand command;

    @BeforeEach
    void setUp() {
        initMocks(this);

        command = new ListCommand();
    }

    @Test
    void execute_callsReceiveMessage() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

    @Test
    void execute_callsGetPlayersNames() {

        command.execute(controller, PLAYER, COMMAND);

        verify(controller).getPlayersNames();
    }

    @Test
    void execute_sendsPlayerNames() {
        String name1 = "name1";
        String name2 = "name2";
        when(controller.getPlayersNames()).thenReturn(new HashSet<>(Arrays.asList(name1, name2)));

        command.execute(controller, PLAYER, COMMAND);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(any(), captor.capture());
        var message = captor.getValue().getMessage();

        assertAll(
                () -> assertTrue(message.contains(name1)),
                () -> assertTrue(message.contains(name2))
        );
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
