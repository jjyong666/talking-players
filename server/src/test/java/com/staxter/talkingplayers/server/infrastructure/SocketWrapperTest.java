package com.staxter.talkingplayers.server.infrastructure;

import com.staxter.talkingplayers.server.infrastructure.reader.SocketReader;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class SocketWrapperTest {

    @Mock
    SocketChannel channel;

    @Mock
    SocketReader reader;

    private SocketWrapper wrapper;

    @BeforeEach
    void setUp() {
        initMocks(this);

        wrapper = new SocketWrapper(channel, reader, 15);
    }

    @Test
    void sendMessage() {
        ServerMessage message = new ServerMessage("asasas");

        wrapper.sendMessage(message);

        verify(channel).sendMessage(message);
    }

    @Test
    @SneakyThrows
    void readObject() {

        wrapper.readObject();

        verify(reader).readObject();
    }

    @Test
    @SneakyThrows
    void close() {

        wrapper.close();

        verify(channel).close();
        verify(reader).close();
    }

}
