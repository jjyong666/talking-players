package com.staxter.talkingplayers.service.manager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.staxter.talkingplayers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TalkManagerMediatorTest {

  private TalkManager manager;

  @BeforeEach
  void setUp() {
    initMocks(this);

    manager = new TalkManagerMediator();
  }

  @Test
  void sendMessage_sendsMessageToOtherPlayer() {
    Player initiator = buildPlayer("initiator");
    manager.registerPlayer(initiator);
    Player replier = buildPlayer("replier");
    manager.registerPlayer(replier);
    String message = "asas";

    manager.sendMessage(message, initiator);

    verify(initiator).receiveMessage(message, replier);
  }

  private Player buildPlayer(String name) {
    Player player = mock(Player.class);
    when(player.getName()).thenReturn(name);
    return player;
  }

  @Test
  void sendMessage_doesNothing_ifReplierIsNotRegistered() {
    Player initiator = buildPlayer("initiator");
    manager.registerPlayer(initiator);

    manager.sendMessage("asas", initiator);

    verifyNoInteractions(initiator);
  }

  @Test
  void sendMessage_doesNothing_ifPlayerMessageLimitIsReached() {
    Player initiator = buildPlayer("initiator");
    when(initiator.getSentCount()).thenReturn(50);
    when(initiator.getReceivedCount()).thenReturn(50);
    manager.registerPlayer(initiator);
    Player replier = buildPlayer("replier");
    manager.registerPlayer(replier);

    manager.sendMessage("asas", initiator);

    verify(initiator, never()).sendMessage(anyString(), any());
    verifyNoInteractions(replier);
  }

}
