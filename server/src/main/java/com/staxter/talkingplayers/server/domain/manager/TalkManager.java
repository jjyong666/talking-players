package com.staxter.talkingplayers.server.domain.manager;

import com.staxter.talkingplayers.server.domain.model.Player;

/**
 * The interface Talk manager.
 */
public interface TalkManager {

    /**
     * Send a message to the specified player from the other player.
     *
     * @param from    the player sending the message
     * @param to      the player receiving the message
     * @param message the message
     */
    void sendMessage(Player from, Player to, String message);

}
