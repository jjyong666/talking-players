package com.staxter.talkingplayers.service.message.receiver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.staxter.talkingplayers.service.message.sender.MessageSender;
import com.staxter.talkingplayers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MessageReceiverImplTest {

  @Mock
  private MessageSender sender;

  private MessageReceiver receiver;

  @BeforeEach
  void setUp() {
    initMocks(this);

    receiver = new MessageReceiverImpl(sender);
  }

  @Test
  void receiveMessage_incrementsReceivedCount() {
    Player to = buildPlayer("to");

    receiver.receiveMessage(buildPlayer("from"), to, "aaaa");

    assertEquals(1, to.getReceivedCount());
  }

  private Player buildPlayer(String name) {
    return new Player(name, sender, null);
  }

  @Test
  void receiveMessage_callsManagerSendMessage_withPlayers() {
    Player from = buildPlayer("from");
    Player to = buildPlayer("to");

    receiver.receiveMessage(from, to, "aaaa");

    verify(sender).sendMessage(eq(to), eq(from), anyString());
  }

  @Test
  void receiveMessage_callsManagerSendMessage_withDecoratedMessage() {
    String message = "aaaa";
    Player to = buildPlayer("to");
    int sentCount = to.getSentCount();

    receiver.receiveMessage(buildPlayer("asas"), to, message);

    verify(sender).sendMessage(any(), any(), eq(message + " " + sentCount));
  }

}
