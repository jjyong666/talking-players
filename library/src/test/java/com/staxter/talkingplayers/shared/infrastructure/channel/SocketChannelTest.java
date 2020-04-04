package com.staxter.talkingplayers.shared.infrastructure.channel;

import com.staxter.talkingplayers.shared.dto.command.CloseCommandMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ObjectOutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class SocketChannelTest {

    @Mock
    ObjectOutputStream stream;

    private SocketChannel channel;

    @BeforeEach
    void setUp() {
        initMocks(this);

        channel = new SocketChannel(stream);
    }

    @Test
    @SneakyThrows
    void sendMessage() {
        doNothing().when(stream).writeObject(any());
        CloseCommandMessage message = new CloseCommandMessage();

        channel.sendMessage(message);

        verify(stream).writeObject(message);
    }

    @Test
    @SneakyThrows
    void close() {

        channel.close();

        verify(stream).close();
    }
}
