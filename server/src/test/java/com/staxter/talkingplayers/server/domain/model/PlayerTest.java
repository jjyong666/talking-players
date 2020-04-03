package com.staxter.talkingplayers.server.domain.model;

import com.staxter.talkingplayers.server.domain.Channel;
import com.staxter.talkingplayers.server.domain.manager.TalkManager;
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
        var message = "asasas";

        player.receiveMessage(message);

        verify(channel).sendMessage(message);
    }

}
