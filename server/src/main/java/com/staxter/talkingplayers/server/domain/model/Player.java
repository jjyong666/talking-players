package com.staxter.talkingplayers.server.domain.model;

import com.staxter.talkingplayers.server.domain.manager.TalkManager;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.PlayerMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Player.
 */
@Getter
@EqualsAndHashCode
public class Player {

    private final Channel channel;

    @Setter
    private String name;
    @Setter
    private TalkManager manager;

    private int sentCount = 0;
    private int receivedCount = 0;

    /**
     * Instantiates a new Player.
     *
     * @param name    the name
     * @param channel the channel
     * @param manager the manager
     */
    public Player(String name, Channel channel, TalkManager manager) {
        this.name = name;
        this.channel = channel;
        this.manager = manager;
    }

    /**
     * Sends a new message.
     *
     * @param message the message
     * @param player  the receiver of the message
     */
    public void sendMessage(String message, Player player) {
        manager.sendMessage(this, player, message);
    }

    /**
     * Receives a message.
     *
     * @param message the message
     */
    public void receiveMessage(MessageDto message) {
        if (message instanceof PlayerMessage) {
            incrementReceivedCount();
        }
        channel.sendMessage(message);
    }

    private void incrementReceivedCount() {
        receivedCount++;
    }

    /**
     * Increment sent count.
     */
    public void incrementSentCount() {
        sentCount++;
    }

    public String toString() {
        return String.format("%s: Sent -> %d, Received <- %d", name, sentCount, receivedCount);
    }

}
