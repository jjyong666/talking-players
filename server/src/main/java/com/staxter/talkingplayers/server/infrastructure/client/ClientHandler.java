package com.staxter.talkingplayers.server.infrastructure.client;

import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import lombok.RequiredArgsConstructor;

import java.net.Socket;

@RequiredArgsConstructor
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final CommandInvoker commandInvoker;

    @Override
    public void run() {

    }

}
