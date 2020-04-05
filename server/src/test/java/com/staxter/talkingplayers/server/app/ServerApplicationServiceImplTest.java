package com.staxter.talkingplayers.server.app;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.domain.service.PlayerService;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ServerApplicationServiceImplTest {

    @Mock
    private PlayerService playerService;

    private ServerApplicationService service;

    @BeforeEach
    void setUp() {
        initMocks(this);

        service = new ServerApplicationServiceImpl(playerService);
    }

    @Test
    void register() {
        var player = mock(Player.class);
        var newName = "newName";

        service.register(player, newName);

        verify(playerService).register(player, newName);
    }

    @Test
    void exists() {
        var name = "name";

        service.exists(name);

        verify(playerService).exists(name);
    }

    @Test
    void delete() {
        var player = new Player("name", mock(Channel.class), null);

        service.delete(player);

        verify(playerService).delete(player);
    }

    @Test
    void sendMessage_retrievesPlayerToSendMessageTo() {
        var player = mock(Player.class);
        var playerTo = "asas";

        service.sendMessage(player, playerTo, "sdsfsfsfsf");

        verify(playerService).find(playerTo);
    }

    @Test
    void sendMessage_failsIfPlayerToDontExists() {
        var player = mock(Player.class);
        when(playerService.find(anyString())).thenReturn(Optional.empty());

        service.sendMessage(player, "asas", "sdsfsfsfsf");

        verify(player, never()).sendMessage(anyString(), any());
        verify(player).receiveMessage(any(ErrorMessage.class));
    }

    @Test
    void getPlayersNames() {

        service.getPlayersNames();

        verify(playerService).getPlayersNames();
    }

    @Test
    void sendMessage_sendsMessage() {
        var player = mock(Player.class);
        var playerTo = new Player("asas", null, null);
        when(playerService.find(anyString())).thenReturn(Optional.of(playerTo));
        var message = "sdsfsfsfsf";

        service.sendMessage(player, "asas", message);

        verify(player).sendMessage(message, playerTo);
    }

    @Test
    void receiveMessage() {
        var player = mock(Player.class);
        var message = new ServerMessage("sdsfsfsfsf");

        service.receiveMessage(player, message);

        verify(player).receiveMessage(message);
    }

}
