package com.staxter.talkingplayers.server.infrastructure.client;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.SocketWrapper;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.dto.command.HelpCommandDto;
import lombok.RequiredArgsConstructor;

import java.io.EOFException;
import java.io.IOException;

@RequiredArgsConstructor
public class PlayerHandler implements Runnable {

    private final SocketWrapper socket;
    private final CommandInvoker commandInvoker;

    private Player player;

    @Override
    public void run() {
        try {
            player = new Player(String.valueOf(socket.getPort()), socket.getChannel(), null);

            System.out.println("Player connected: " + player.getName());
            commandInvoker.processCommand(new ServerCommandDto("Welcome to the Chat!"), player);
            commandInvoker.processCommand(new HelpCommandDto(), player);

            Object object;
            while (true) {
                synchronized (this) {
                    if ((object = socket.readObject()) == null) {
                        closePlayerSession();
                        break;
                    }
                }

                if (object instanceof CommandDto && commandInvoker.processCommand((CommandDto) object, player))
                    break;
            }
        } catch (EOFException e) {
            closePlayerSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closePlayerSession() {
        commandInvoker.processCommand(new CloseCommandDto(), player);
    }

}
