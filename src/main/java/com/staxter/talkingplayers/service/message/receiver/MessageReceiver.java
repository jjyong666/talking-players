package com.staxter.talkingplayers.service.message.receiver;

import com.staxter.talkingplayers.model.Player;

/**
 * The interface Message receiver.
 */
public interface MessageReceiver {

  /**
   * Receives a message.
   *
   * @param from the sender
   * @param to the receiver
   * @param message the message
   */
  void receiveMessage(Player from, Player to, String message);

}
