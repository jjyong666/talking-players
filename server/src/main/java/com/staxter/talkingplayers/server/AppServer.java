package com.staxter.talkingplayers.server;

import com.staxter.talkingplayers.server.infrastructure.factory.AppFactory;
import com.staxter.talkingplayers.server.infrastructure.server.SocketServerImpl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type App.
 */
public class AppServer {

    /**
     * The constant MESSAGE_LIMIT.
     */
    public static final String MESSAGE_LIMIT = "MESSAGE_LIMIT";
    private static final int PORT = 50000;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws IOException {

        if (System.getenv(MESSAGE_LIMIT) == null) {
            System.out.println("Please define the env var " + MESSAGE_LIMIT);
            return;
        }

        var commandInvoker = AppFactory.buildCommandInvoker();
        ExecutorService pool = null;
        try {
            pool = Executors.newFixedThreadPool(20);

            new SocketServerImpl(commandInvoker, pool).startServer(PORT);
        } finally {
            if (pool != null)
                pool.shutdown();
        }

    }

}
