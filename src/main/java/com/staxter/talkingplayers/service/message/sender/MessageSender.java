package com.staxter.talkingplayers.service.message.sender;

import com.staxter.talkingplayers.model.Player;

/**
 * The interface Message sender.
 */
public interface MessageSender {

  /**
   * Sends a message.
   *
   * @param from the sender
   * @param to the receiver
   * @param message the message
   */
  void sendMessage(Player from, Player to, String message);

}
