package com.staxter.talkingplayers;

import com.staxter.talkingplayers.model.Player;
import com.staxter.talkingplayers.service.config.MessageLimitConfig;
import com.staxter.talkingplayers.service.manager.TalkManager;
import com.staxter.talkingplayers.service.manager.TalkManagerMediator;
import com.staxter.talkingplayers.service.message.receiver.MessageReceiver;
import com.staxter.talkingplayers.service.message.receiver.MessageReceiverImpl;
import com.staxter.talkingplayers.service.message.sender.MessageSender;
import com.staxter.talkingplayers.service.message.sender.MessageSenderImpl;

/**
 * The type App.
 */
public class App {

  /**
   * The constant MESSAGE_LIMIT.
   */
  public static final String MESSAGE_LIMIT = "MESSAGE_LIMIT";

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    if (System.getenv(MESSAGE_LIMIT) == null) {
      System.out.println("Please define the env var " + MESSAGE_LIMIT);
      return;
    }

    TalkManager manager = new TalkManagerMediator(new MessageLimitConfig());
    MessageSender sender = new MessageSenderImpl(manager);
    MessageReceiver receiver = new MessageReceiverImpl(sender);

    Player initiator = new Player("initiator", sender, receiver);
    manager.registerPlayer(initiator);

    Player replier = new Player("replier", sender, receiver);
    manager.registerPlayer(replier);

    initiator.sendMessage("oh", replier);

  }

}
