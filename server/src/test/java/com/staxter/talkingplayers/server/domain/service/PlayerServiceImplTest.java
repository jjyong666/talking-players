package com.staxter.talkingplayers.server.domain.service;

import com.staxter.talkingplayers.server.domain.manager.TalkManager;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerServiceImplTest {

    @Mock
    private PlayerRepository repository;

    @Mock
    private TalkManager manager;

    private PlayerService service;

    @BeforeEach
    void setUp() {
        initMocks(this);

        service = new PlayerServiceImpl(repository, manager);
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
    void find_callsRepositoryFind() {
        var name = "name";

        service.find(name);

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
    void getPlayersNames_callsRepository_listPlayers() {
        service.getPlayersNames();

        verify(repository).listPlayers();
    }

    @Test
    void listPlayers_sendsPlayerList() {
        HashSet<String> names = new HashSet<>(Arrays.asList("asas", "zdsd"));
        when(repository.listPlayers()).thenReturn(names);

        var result = service.getPlayersNames();

        assertEquals(names, result);
    }

}
