package com.staxter.talkingplayers.server.infrastructure.server;

import java.io.IOException;

/**
 * The interface Socket server.
 */
public interface SocketServer {

    /**
     * Starts a socket server.
     *
     * @param port the port
     * @throws IOException the io exception
     */
    void startServer(int port) throws IOException;
}
