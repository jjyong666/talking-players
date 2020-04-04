package com.staxter.talkingplayers.shared.infrastructure.channel;

import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.Message;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.ObjectOutputStream;

@RequiredArgsConstructor
public class SocketChannel implements Channel {

    private final ObjectOutputStream writer;

    @Override
    public void sendMessage(Message message) {
        try {
            writer.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
