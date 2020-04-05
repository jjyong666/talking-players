package com.staxter.talkingplayers.server.infrastructure.server;

import com.staxter.talkingplayers.server.infrastructure.client.ClientHandler;
import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
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
                executorService.execute(new ClientHandler(listener.accept(), commandInvoker));
            }
        }
    }

}
