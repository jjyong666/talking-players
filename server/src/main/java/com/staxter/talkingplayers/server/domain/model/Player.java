package com.staxter.talkingplayers.server.domain.model;

import com.staxter.talkingplayers.server.domain.Channel;
import com.staxter.talkingplayers.server.domain.manager.TalkManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type Player.
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Player {

    private final TalkManager manager;
    private final Channel channel;

    @Setter
    @Accessors(chain = true)
    private String name;
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
    public void receiveMessage(String message) {
        channel.sendMessage(message);
    }

    /**
     * Increment sent count.
     */
    public void incrementSentCount() {
        sentCount++;
    }

    /**
     * Increment received count.
     */
    public void incrementReceivedCount() {
        receivedCount++;
    }

    public String toString() {
        return String.format("%s: Sent -> %d, Received <- %d", name, sentCount, receivedCount);
    }

}
