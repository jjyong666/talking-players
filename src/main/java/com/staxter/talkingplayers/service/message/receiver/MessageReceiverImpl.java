package com.staxter.talkingplayers.service.message.receiver;

import com.staxter.talkingplayers.model.Player;
import com.staxter.talkingplayers.service.message.sender.MessageSender;

/**
 * The type Message receiver.
 *
 * This class allows a Player to receive a message.
 * Received messages are decorated and then replied back to the sender.
 */
public class MessageReceiverImpl implements MessageReceiver {

  private final MessageSender sender;

  /**
   * Instantiates a new Message receiver.
   *
   * @param sender the sender
   */
  public MessageReceiverImpl(MessageSender sender) {
    this.sender = sender;
  }

  @Override
  public void receiveMessage(Player from, Player to, String message) {
    to.incrementReceivedCount();
    logCompletedOperation(from, to, message);
    sender.sendMessage(to, from, decorateMessage(message, to.getSentCount()));
  }

  private void logCompletedOperation(Player from, Player to, String message) {
    System.out.println(String.format("Player %s sent message: \"%s\" to Player %s", from.getName(), message, to.getName()));
    logPlayerStatistics(from);
    System.out.println(String.format("Player %s received message \"%s\" from Player %s", to.getName(), message, from.getName()));
    logPlayerStatistics(to);
  }

  private void logPlayerStatistics(Player player) {
    System.out.println(player.toString());
  }

  private String decorateMessage(String message, int sentCount) {
    return String.format("%s %d", message, sentCount);
  }

}
