package com.staxter.talkingplayers.shared.dto;

public class OwnMessage extends MessageDto {

    private static final long serialVersionUID = 752776147L;

    public OwnMessage(String message) {
        super(message);
    }

    @Override
    public String toPrint() {
        return String.format("You: %s", message);
    }

}
