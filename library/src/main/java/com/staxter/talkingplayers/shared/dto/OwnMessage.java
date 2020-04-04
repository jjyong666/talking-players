package com.staxter.talkingplayers.shared.dto;

public class OwnMessage extends Message {

    private static final long serialVersionUID = 752776147L;

    public OwnMessage(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return String.format("You: %s", message);
    }

}
