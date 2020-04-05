package com.staxter.talkingplayers.client.api;

import com.staxter.talkingplayers.client.app.ClientApplicationService;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ClientControllerImplTest {

    @Mock
    private ClientApplicationService service;

    private ClientController controller;

    @BeforeEach
    void setUp() {
        initMocks(this);

        controller = new ClientControllerImpl(service);
    }

    @Test
    void receiveMessage() {
        var message = new ServerMessage("sdsdsd");
        var channel = new SocketChannel(null);

        controller.receiveMessage(message, channel);

        verify(service).receiveMessage(message, channel);
    }

    @Test
    void sendCommand() {
        var command = new CloseCommandDto();
        var channel = new SocketChannel(null);

        controller.sendCommand(command, channel);

        verify(service).sendCommand(command, channel);
    }

}
