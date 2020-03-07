package com.staxter.talkingplayers.model;

import com.staxter.talkingplayers.service.message.receiver.MessageReceiver;
import com.staxter.talkingplayers.service.message.sender.MessageSender;

/**
 * The type Player.
 */
public class Player {

  private final MessageSender sender;
  private final MessageReceiver receiver;

  private String name;
  private int sentCount = 0;
  private int receivedCount = 0;

  /**
   * Instantiates a new Player.
   *
   * @param name the name
   * @param sender the sender
   * @param receiver the receiver
   */
  public Player(String name, MessageSender sender, MessageReceiver receiver) {
    this.name = name;
    this.sender = sender;
    this.receiver = receiver;
  }

  /**
   * Sends a new message.
   *
   * @param message the message
   * @param player the receiver of the message
   */
  public void sendMessage(String message, Player player) {
    sender.sendMessage(this, player, message);
  }

  /**
   * Receives and replies message.
   *
   * @param message the message
   * @param player the who sends the message
   */
  public void receiveMessage(String message, Player player) {
    receiver.receiveMessage(player, this, message);
  }

  /**
   * Gets sent count.
   *
   * @return the sent count
   */
  public int getSentCount() {
    return sentCount;
  }

  /**
   * Increment sent count.
   */
  public void incrementSentCount() {
    sentCount++;
  }

  /**
   * Gets received count.
   *
   * @return the received count
   */
  public int getReceivedCount() {
    return receivedCount;
  }

  /**
   * Increment received count.
   */
  public void incrementReceivedCount() {
    receivedCount++;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  public String toString() {
    return String.format("%s: Sent -> %d, Received <- %d", name, sentCount, receivedCount);
  }

}
