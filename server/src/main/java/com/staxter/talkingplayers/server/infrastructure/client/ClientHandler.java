package com.staxter.talkingplayers.server.infrastructure.client;

import com.staxter.talkingplayers.server.api.ServerController;
import lombok.RequiredArgsConstructor;

import java.net.Socket;

@RequiredArgsConstructor
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final ServerController controller;

    @Override
    public void run() {

    }

}
