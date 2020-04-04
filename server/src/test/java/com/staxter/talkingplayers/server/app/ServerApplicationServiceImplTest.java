package com.staxter.talkingplayers.server.app;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.domain.repository.PlayerRepository;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ServerApplicationServiceImplTest {

    @Mock
    private PlayerRepository repository;

    private ServerApplicationService service;

    @BeforeEach
    void setUp() {
        initMocks(this);

        service = new ServerApplicationServiceImpl(repository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void register_invalidName(String newName) {
        var player = mock(Player.class);
        when(player.getName()).thenReturn("asas");
        when(repository.find(anyString())).thenReturn(Optional.empty());

        service.register(player, newName);

        verify(repository, never()).save(any());
        verify(player).receiveMessage(any(ErrorMessage.class));
    }

    @Test
    void register_invalidName_alreadyExists() {
        var player = mock(Player.class);
        var newName = "newName";
        when(repository.find(newName)).thenReturn(Optional.of(new Player("asas", null, null)));

        service.register(player, newName);

        verify(repository, never()).save(any());
        verify(player).receiveMessage(any(ErrorMessage.class));
    }

    @Test
    void register_playerShouldRegisterOnlyOnce() {
        var player = mock(Player.class);
        when(player.getName()).thenReturn("asas");
        when(repository.find(anyString())).thenReturn(Optional.of(new Player("asas", null, null)));

        service.register(player, "name");

        verify(repository, never()).save(any());
        verify(player).receiveMessage(any(ServerMessage.class));
    }

    @Test
    void register_savesPlayerWithNewName() {
        var player = new Player("jaja", mock(Channel.class), null);
        var newName = "name";
        when(repository.save(any())).thenAnswer(x -> x.getArgument(0));

        service.register(player, newName);

        var captor = ArgumentCaptor.forClass(Player.class);
        verify(repository).save(captor.capture());
        assertEquals(newName, captor.getValue().getName());
    }

    @Test
    void exists_callsRepositoryFind() {
        var name = "name";

        service.exists(name);

        verify(repository).find(name);
    }

    @Test
    void delete() {
        var name = "name";
        var player = new Player(name, mock(Channel.class), null);

        service.delete(player);

        verify(repository).delete(name);
    }

    @Test
    void sendMessage_retrievesPlayerToSendMessageTo() {
        var player = mock(Player.class);
        var playerTo = "asas";

        service.sendMessage(player, playerTo, "sdsfsfsfsf");

        verify(repository).find(playerTo);
    }

    @Test
    void sendMessage_failsIfPlayerToDontExists() {
        var player = mock(Player.class);
        when(repository.find(anyString())).thenReturn(Optional.empty());

        service.sendMessage(player, "asas", "sdsfsfsfsf");

        verify(player, never()).sendMessage(anyString(), any());
        verify(player).receiveMessage(any(ErrorMessage.class));
    }

    @Test
    void sendMessage_sendsMessage() {
        var player = mock(Player.class);
        var playerTo = new Player("asas", null, null);
        when(repository.find(anyString())).thenReturn(Optional.of(playerTo));
        var message = "sdsfsfsfsf";

        service.sendMessage(player, "asas", message);

        verify(player).sendMessage(message, playerTo);
    }

    @Test
    void listPlayers_callsRepository_listPlayers() {
        service.listPlayers(new Player("asas", mock(Channel.class), null));

        verify(repository).listPlayers();
    }

    @Test
    void listPlayers_sendsPlayerList() {
        var player = mock(Player.class);
        var name1 = "asas";
        var name2 = "zdsd";
        when(repository.listPlayers()).thenReturn(new HashSet<>(Arrays.asList(name1, name2)));

        service.listPlayers(player);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(player).receiveMessage(captor.capture());
        String message = captor.getValue().getMessage();
        assertTrue(message.contains(name1));
        assertTrue(message.contains(name2));
    }

}
