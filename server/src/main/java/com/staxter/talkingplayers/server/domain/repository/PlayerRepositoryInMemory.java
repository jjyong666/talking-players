package com.staxter.talkingplayers.server.domain.repository;

import com.staxter.talkingplayers.server.domain.model.Player;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The type Player repository in memory.
 */
public class PlayerRepositoryInMemory implements PlayerRepository {

    private ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();

    @Override
    public Player save(Player player) {
        players.put(player.getName(), player);
        return player;
    }

    @Override
    public Set<String> listPlayers() {
        return players.keySet();
    }

    @Override
    public Optional<Player> find(String name) {
        return Optional.ofNullable(players.get(name));
    }

    @Override
    public void delete(String name) {
        players.remove(name);
    }

}
