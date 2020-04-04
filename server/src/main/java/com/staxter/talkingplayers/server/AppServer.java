package com.staxter.talkingplayers.server;

/**
 * The type App.
 */
public class AppServer {

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
        }

    }

}
