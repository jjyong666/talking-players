package com.staxter.talkingplayers.service.message.sender;

import com.staxter.talkingplayers.model.Player;
import com.staxter.talkingplayers.service.manager.TalkManager;

/**
 * The type Message sender.
 *
 * Passes the given message from one Player to the TalkManager.
 */
public class MessageSenderImpl implements MessageSender {

  private final TalkManager manager;

  /**
   * Instantiates a new Message sender.
   *
   * @param manager the manager
   */
  public MessageSenderImpl(TalkManager manager) {
    this.manager = manager;
  }

  @Override
  public void sendMessage(Player from, Player to, String message) {
    from.incrementSentCount();

    manager.sendMessage(message, to);
  }

}
