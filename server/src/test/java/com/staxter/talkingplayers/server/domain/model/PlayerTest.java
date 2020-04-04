package com.staxter.talkingplayers.server.domain.model;

import com.staxter.talkingplayers.server.domain.manager.TalkManager;
import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.PlayerMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerTest {

    @Mock
    private TalkManager manager;

    @Mock
    private Channel channel;

    private Player player;

    @BeforeEach
    void setUp() {
        initMocks(this);

        player = new Player("A", channel, manager);
    }

    @Test
    void newPlayer_sentCount_isZero() {

        assertEquals(0, player.getSentCount());
    }

    @Test
    void newPlayer_receivedCount_isZero() {

        assertEquals(0, player.getReceivedCount());
    }

    @Test
    void sendMessage_callsTalkManager() {
        var message = "aaaa";
        var to = new Player("aa", channel, manager);

        player.sendMessage(message, to);

        verify(manager).sendMessage(player, to, message);
    }

    @Test
    void receiveMessage_callsChannelSendMessage() {
        var message = new ServerMessage("asasas");

        player.receiveMessage(message);

        verify(channel).sendMessage(message);
    }

    @Test
    void receiveMessage_incrementsReceivedCount_onlyWithPlayerMessages() {
        var message = new PlayerMessage("asasas", "asasas");
        int receivedCount = player.getReceivedCount();

        player.receiveMessage(message);

        assertEquals(receivedCount + 1, player.getReceivedCount());
    }

    @Test
    void receiveMessage_otherMessagesDontIncreaseReceivedCount() {
        var message = new ErrorMessage("asasas");
        int receivedCount = player.getReceivedCount();

        player.receiveMessage(message);

        assertEquals(receivedCount, player.getReceivedCount());
    }

}
