package com.staxter.talkingplayers.shared.domain;

import com.staxter.talkingplayers.shared.dto.Message;

import java.io.Closeable;

public interface Channel extends Closeable {

    void sendMessage(Message message);

}
