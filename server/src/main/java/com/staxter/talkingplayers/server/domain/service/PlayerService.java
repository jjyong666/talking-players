package com.staxter.talkingplayers.server.domain.service;

import com.staxter.talkingplayers.server.domain.model.Player;

import java.util.Optional;
import java.util.Set;

public interface PlayerService {

    /**
     * Registers a player.
     *
     * @param player the player
     * @param name   the name
     * @return the player
     */
    Player register(Player player, String name);

    /**
     * Exists a player.
     *
     * @param name   the name
     * @return the boolean.
     */
    boolean exists(String name);

    /**
     * Returns existing player.
     *
     * @param name the name
     * @return the optional player
     */
    Optional<Player> find(String name);

    /**
     * Deletes a player.
     *
     * @param player the player
     */
    void delete(Player player);

    /**
     * List existing players names.
     */
    Set<String> getPlayersNames();

}
