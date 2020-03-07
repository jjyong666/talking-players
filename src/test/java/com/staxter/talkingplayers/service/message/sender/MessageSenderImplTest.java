package com.staxter.talkingplayers.service.message.sender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.staxter.talkingplayers.service.manager.TalkManager;
import com.staxter.talkingplayers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MessageSenderImplTest {

  @Mock
  private TalkManager manager;

  private MessageSender sender;

  @BeforeEach
  void setUp() {
    initMocks(this);

    sender = new MessageSenderImpl(manager);
  }

  @Test
  void sendMessage_incrementsSentCount() {
    Player from = buildPlayer();

    sender.sendMessage(from, buildPlayer(),"aaaa");

    assertEquals(1, from.getSentCount());
  }

  private Player buildPlayer() {
    return new Player("asas", sender, null);
  }

  @Test
  void sendMessage_callsManagerSendMessage() {
    Player to = buildPlayer();
    String message = "aaaa";

    sender.sendMessage(buildPlayer(), to,message);

    verify(manager).sendMessage(message, to);
  }

}
