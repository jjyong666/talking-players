package com.staxter.talkingplayers.server.domain.repository;

import com.staxter.talkingplayers.server.domain.model.Player;

import java.util.Optional;
import java.util.Set;

/**
 * The interface Player repository.
 */
public interface PlayerRepository {

    /**
     * Saves a player.
     *
     * @param player the player
     * @return the player
     */
    Player save(Player player);

    /**
     * List existing players.
     *
     * @return the set
     */
    Set<String> listPlayers();

    /**
     * Find a player by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Player> find(String name);

    /**
     * Deletes a player.
     *
     * @param name the name
     */
    void delete(String name);
}
