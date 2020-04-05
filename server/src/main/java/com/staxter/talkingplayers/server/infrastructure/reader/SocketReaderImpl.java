package com.staxter.talkingplayers.server.infrastructure.reader;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;

@RequiredArgsConstructor
public class SocketReaderImpl implements SocketReader {

    private final ObjectInputStream inputStream;

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
