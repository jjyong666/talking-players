package com.staxter.talkingplayers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.staxter.talkingplayers.service.message.receiver.MessageReceiver;
import com.staxter.talkingplayers.service.message.sender.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class PlayerTest {

  @Mock
  private MessageSender sender;
  @Mock
  private MessageReceiver receiver;

  private Player player;

  @BeforeEach
  void setUp() {
    initMocks(this);

    player = new Player("A", sender, receiver);
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
  void sendMessage_callsSender() {
    String message = "aaaa";
    Player to = new Player("aa", sender, receiver);

    player.sendMessage(message, to);

    verify(sender).sendMessage(player, to, message);
  }

  @Test
  void receiveMessage_callsManagerSendMessage() {
    String message = "aaaa";
    Player to = new Player("aa", sender, receiver);

    player.receiveMessage(message, to);

    verify(receiver).receiveMessage(to, player, message);
  }

}
