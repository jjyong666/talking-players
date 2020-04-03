package com.staxter.talkingplayers.server.domain.manager;

import com.staxter.talkingplayers.server.domain.config.MessageLimitConfig;
import com.staxter.talkingplayers.server.domain.model.Player;

/**
 * The type Talk manager mediator.
 */
public class TalkManagerMediator implements TalkManager {

    private final MessageLimitConfig config;

    /**
     * Instantiates a new Talk manager mediator.
     *
     * @param config the message limit config
     */
    public TalkManagerMediator(MessageLimitConfig config) {
        this.config = config;
    }

    @Override
    public void sendMessage(Player from, Player to, String message) {
        int messageLimit = config.getMessageLimit();
        if (from.getSentCount() >= messageLimit) {
            from.receiveMessage("You cannot send more messages");
            return;
        }
        if (to.getReceivedCount() >= messageLimit) {
            from.receiveMessage(to.getName() + " cannot receive more messages");
            return;
        }

        to.receiveMessage(message);
        from.incrementSentCount();
    }

}
