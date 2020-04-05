package com.staxter.talkingplayers.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MessageDto implements Message {

    private static final long serialVersionUID = 752776147L;

    protected String message;

    @Override
    public String toString() {
        return message;
    }

    public String toPrint() {
        return message;
    }
}
