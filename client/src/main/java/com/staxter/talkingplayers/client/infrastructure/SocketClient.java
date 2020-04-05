package com.staxter.talkingplayers.client.infrastructure;

import com.staxter.talkingplayers.client.api.ClientController;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.domain.command.CommandBuilder;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.RequiredArgsConstructor;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SocketClient {

    private final ClientController controller;

    private final Object lock = new Object();
    private volatile boolean finish = false;

    public void start() throws IOException {
        try (var socket = new Socket("127.0.0.1", 50000);
             var channel = new SocketChannel(new ObjectOutputStream(socket.getOutputStream()));
             var reader = new ObjectInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in)) {

            while (true) {
                processIncomingMessages(reader, channel);

                String next = scanner.nextLine();
                Optional<CommandDto> commandMessage = CommandBuilder.build(next);
                if (commandMessage.isEmpty())
                    continue;
                controller.sendCommand(commandMessage.get(), channel);

                if (next.equals(CloseCommandDto.COMMAND) || finish) {
                    synchronized (lock) {
                        finish = true;
                    }
                    break;
                }
            }
        }
    }

    private void processIncomingMessages(ObjectInputStream reader, Channel channel) {
        CompletableFuture.runAsync(() -> {
            Object object;
            try {
                while (true) {
                    synchronized (lock) {
                        if ((object = reader.readObject()) == null || finish) {
                            finish = true;
                            break;
                        }
                    }
                    if (object instanceof MessageDto) {
                        if (controller.receiveMessage((MessageDto) object, channel)) {
                            synchronized (lock) {
                                finish = true;
                            }
                            break;
                        }
                    } else {
                        System.out.println("Skipped: " + object);
                    }
                }
            } catch (EOFException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
