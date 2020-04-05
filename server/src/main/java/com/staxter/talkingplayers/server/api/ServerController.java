package com.staxter.talkingplayers.server.api;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.MessageDto;

import java.util.Set;

/**
 * The interface Server controller.
 */
public interface ServerController {

    /**
     * Registers a player.
     *
     * @param player the player
     * @param name   the name
     * @return the player
     */
    Player registerPlayer(Player player, String name);

    void receiveMessage(Player player, MessageDto message);

    /**
     * Deletes a player.
     *
     * @param player the player
     */
    void deletePlayer(Player player);

    /**
     * List existing players.
     */
    Set<String> getPlayersNames();

    /**
     * Exists player.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsPlayer(String name);

    /**
     * Sends a message.
     *
     * @param from    the from
     * @param to      the to
     * @param message the message
     */
    void sendMessage(Player from, String to, String message);

}
