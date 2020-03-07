package com.staxter.talkingplayers.service.manager;

import com.staxter.talkingplayers.model.Player;

/**
 * The interface Talk manager.
 */
public interface TalkManager {

  /**
   * Send a message to the specified player from the other player.
   *
   * @param message the message
   * @param player the player
   */
  void sendMessage(String message, Player player);

  /**
   * Register a player.
   * First player registered will be the initiator.
   * Nexts players registered will be the replier.
   *
   * @param player the player
   */
  void registerPlayer(Player player);

}
