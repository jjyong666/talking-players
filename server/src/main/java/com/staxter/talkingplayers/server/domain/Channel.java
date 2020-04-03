package com.staxter.talkingplayers.server.domain;

import java.io.Closeable;

public interface Channel extends Closeable {

    void sendMessage(String message);

}
