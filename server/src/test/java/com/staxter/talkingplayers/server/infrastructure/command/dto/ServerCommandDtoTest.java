package com.staxter.talkingplayers.server.infrastructure.command.dto;

import com.staxter.talkingplayers.shared.dto.ServerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerCommandDtoTest {

    private static final String MESSAGE = "asasasa";

    private ServerCommandDto serverCommandDto;

    @BeforeEach
    void setUp() {

        serverCommandDto = new ServerCommandDto(MESSAGE);
    }

    @Test
    void getMessage_returnsServerMessage() {

        serverCommandDto = new ServerCommandDto(MESSAGE);

        assertTrue(serverCommandDto.getMessage() instanceof ServerMessage);
        assertEquals(MESSAGE, serverCommandDto.getMessage().getMessage());
    }

}
