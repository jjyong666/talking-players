package com.staxter.talkingplayers.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message implements Serializable {

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
