package com.staxter.talkingplayers.server.domain.manager;

import com.staxter.talkingplayers.server.domain.config.MessageLimitConfig;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.PlayerMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TalkManagerMediatorTest {

    private static final int MESSAGE_LIMIT = 3;

    @Mock
    private MessageLimitConfig config;

    private TalkManager manager;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMessageLimit();

        manager = new TalkManagerMediator(config);
    }

    private void mockMessageLimit() {
        when(config.getMessageLimit()).thenReturn(MESSAGE_LIMIT);
    }

    @Test
    void sendMessage_sendsMessageToOtherPlayer() {
        Player initiator = buildPlayer("initiator");
        Player replier = buildPlayer("replier");
        String message = "asas";

        manager.sendMessage(initiator, replier, message);

        var captor = ArgumentCaptor.forClass(PlayerMessage.class);
        verify(replier).receiveMessage(captor.capture());
        PlayerMessage playerMessage = captor.getValue();
        assertEquals(message, playerMessage.getMessage());
        assertEquals(initiator.getName(), playerMessage.getPlayer());
    }

    private Player buildPlayer(String name) {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn(name);
        return player;
    }

    @Test
    void sendMessage_incrementsMessagesCounter() {
        Player initiator = buildPlayer("initiator");
        Player replier = buildPlayer("replier");
        String message = "asas";

        manager.sendMessage(initiator, replier, message);

        verify(initiator).incrementSentCount();
    }

    @Test
    void sendMessage_doesNothing_ifSenderSentLimitIsReached() {
        Player initiator = buildPlayer("initiator");
        when(initiator.getSentCount()).thenReturn(MESSAGE_LIMIT + 5);
        Player replier = buildPlayer("replier");

        manager.sendMessage(initiator, replier, "message");

        verify(initiator).getSentCount();
        verify(replier, never()).receiveMessage(any());
    }

    @Test
    void sendMessage_doesNothing_ifReplierReceivedLimitIsReached() {
        Player initiator = buildPlayer("initiator");
        Player replier = buildPlayer("replier");
        when(replier.getReceivedCount()).thenReturn(MESSAGE_LIMIT + 5);

        manager.sendMessage(initiator, replier, "message");

        verify(replier).getReceivedCount();
        verify(replier, never()).receiveMessage(any());
    }

}
