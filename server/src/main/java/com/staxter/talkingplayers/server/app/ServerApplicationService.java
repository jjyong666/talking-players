package com.staxter.talkingplayers.server.app;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.domain.Channel;

/**
 * The interface Server application service.
 */
public interface ServerApplicationService {

    /**
     * Registers a player.
     *
     * @param player the player
     * @param name   the name
     * @return the player
     */
    Player register(Player player, String name);

    /**
     * Exists players.
     *
     * @param name the name
     * @return the boolean
     */
    boolean exists(String name);

    /**
     * Deletes a player.
     *
     * @param player the player
     */
    void delete(Player player);

    /**
     * Send message to another player.
     *
     * @param from    the from
     * @param to      the to
     * @param message the message
     */
    void sendMessage(Player from, String to, String message);

    /**
     * List existing players.
     *
     * @param player the player
     */
    void listPlayers(Player player);

    /**
     * Builds a player instance.
     *
     * @param name    the name
     * @param channel the channel
     * @return the player
     */
    Player buildPlayer(String name, Channel channel);

}
