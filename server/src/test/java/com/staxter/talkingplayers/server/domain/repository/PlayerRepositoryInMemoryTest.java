package com.staxter.talkingplayers.server.domain.repository;

import com.staxter.talkingplayers.server.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerRepositoryInMemoryTest {

    private PlayerRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PlayerRepositoryInMemory();
    }

    @Test
    void save_returnsSavedPlayer() {
        Player player = new Player("asas", null, null);

        Player result = repository.save(player);

        assertEquals(player, result);
    }

    @Test
    void save_savesPlayer() {
        String name = "asas";
        Player player = new Player(name, null, null);

        repository.save(player);

        assertEquals(player, repository.find(name).orElseThrow());
    }

    @Test
    void listPlayers() {
        String name = "asas";
        repository.save(new Player(name, null, null));
        String name1 = "sfvdfg";
        repository.save(new Player(name1, null, null));

        Set<String> players = repository.listPlayers();

        assertEquals(2, players.size());
        assertTrue(players.containsAll(Arrays.asList(name, name1)));
    }

    @Test
    void find() {
        String name = "asas";
        Player player = new Player(name, null, null);
        repository.save(player);

        Player result = repository.find(name).orElseThrow();

        assertEquals(player, result);
    }

    @Test
    void delete() {
        String name = "asas";
        Player player = new Player(name, null, null);
        repository.save(player);

        repository.delete(name);

        assertTrue(repository.find(name).isEmpty());
    }
}
