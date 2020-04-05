package com.staxter.talkingplayers.server.infrastructure;

import com.staxter.talkingplayers.server.infrastructure.reader.SocketReader;
import com.staxter.talkingplayers.shared.dto.Message;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.IOException;

@RequiredArgsConstructor
public class SocketWrapper implements Closeable {

    @Getter
    private final SocketChannel channel;
    private final SocketReader reader;
    @Getter
    private final int port;

    public void sendMessage(Message message) {
        channel.sendMessage(message);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return reader.readObject();
    }

    @Override
    public void close() throws IOException {
        channel.close();
        reader.close();
    }
}
