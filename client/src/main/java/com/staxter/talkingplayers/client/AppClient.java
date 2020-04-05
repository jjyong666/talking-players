package com.staxter.talkingplayers.client;

import com.staxter.talkingplayers.client.api.ClientControllerImpl;
import com.staxter.talkingplayers.client.app.ClientApplicationServiceImpl;
import com.staxter.talkingplayers.client.infrastructure.SocketClient;

/**
 * The type App.
 */
public class AppClient {

    public static void main(String[] args) throws Exception {

        new SocketClient(
                new ClientControllerImpl(
                        new ClientApplicationServiceImpl()))
                .start();
    }

}
