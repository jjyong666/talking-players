package com.staxter.talkingplayers.server.infrastructure.server;

import com.staxter.talkingplayers.server.infrastructure.SocketWrapper;
import com.staxter.talkingplayers.server.infrastructure.client.PlayerHandler;
import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import com.staxter.talkingplayers.server.infrastructure.reader.SocketReaderImpl;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.RequiredArgsConstructor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

/**
 * The type Socket server.
 */
@RequiredArgsConstructor
public class SocketServerImpl implements SocketServer {

    private final CommandInvoker commandInvoker;
    private final ExecutorService executorService;

    @Override
    public void startServer(int port) throws IOException {
        System.out.println("The chat server is running on PORT: " + port);

        try (var listener = new ServerSocket(port)) {
            while (true) {
                var socket = listener.accept();

                var wrapper = new SocketWrapper(
                        new SocketChannel(new ObjectOutputStream(socket.getOutputStream())),
                        new SocketReaderImpl(new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))),
                        socket.getPort());
                executorService.execute(new PlayerHandler(wrapper, commandInvoker));
            }
        }
    }

}
