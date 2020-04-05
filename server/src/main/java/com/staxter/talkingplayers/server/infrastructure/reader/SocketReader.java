package com.staxter.talkingplayers.server.infrastructure.reader;

import java.io.Closeable;
import java.io.IOException;

public interface SocketReader extends Closeable {

    Object readObject() throws IOException, ClassNotFoundException;

}
