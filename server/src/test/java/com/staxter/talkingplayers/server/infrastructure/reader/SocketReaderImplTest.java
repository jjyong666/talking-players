package com.staxter.talkingplayers.server.infrastructure.reader;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ObjectInputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class SocketReaderImplTest {

    @Mock
    ObjectInputStream stream;

    private SocketReader reader;

    @BeforeEach
    void setUp() {
        initMocks(this);

        reader = new SocketReaderImpl(stream);
    }

    @Test
    @SneakyThrows
    void readObject() {

        reader.readObject();

        verify(stream).readObject();
    }

    @Test
    @SneakyThrows
    void close() {

        reader.close();

        verify(stream).close();
    }

}
