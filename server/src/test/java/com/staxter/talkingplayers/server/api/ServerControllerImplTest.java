package com.staxter.talkingplayers.server.api;

import com.staxter.talkingplayers.server.app.ServerApplicationService;
import com.staxter.talkingplayers.server.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ServerControllerImplTest {

    @Mock
    private ServerApplicationService service;

    private ServerController controller;

    @BeforeEach
    void setUp() {
        initMocks(this);

        controller = new ServerControllerImpl(service);
    }

    @Test
    void registerPlayer() {
        var player = mock(Player.class);
        var name = "asas";

        controller.registerPlayer(player, name);

        verify(service).register(player, name);
    }

    @Test
    void getPlayersNames() {

        controller.getPlayersNames();

        verify(service).getPlayersNames();
    }

    @Test
    void existsPlayer() {
        var name = "asas";

        controller.existsPlayer(name);

        verify(service).exists(name);
    }

    @Test
    void sendMessage() {
        var player = mock(Player.class);
        var playerTo = "sdsdsd";
        var message = "asasxxxxxx";

        controller.sendMessage(player, playerTo, message);

        verify(service).sendMessage(player, playerTo, message);
    }

    @Test
    void deletePlayer() {
        var player = mock(Player.class);

        controller.deletePlayer(player);

        verify(service).delete(player);
    }

}
