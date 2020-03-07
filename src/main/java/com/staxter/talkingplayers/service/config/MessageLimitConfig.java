package com.staxter.talkingplayers.service.config;

import com.staxter.talkingplayers.App;

/**
 * The type Message limit config.
 */
public class MessageLimitConfig {

  /**
   * Gets message limit.
   *
   * @return the message limit
   */
  public int getMessageLimit() {
    return Integer.parseInt(System.getenv(App.MESSAGE_LIMIT));
  }

}
