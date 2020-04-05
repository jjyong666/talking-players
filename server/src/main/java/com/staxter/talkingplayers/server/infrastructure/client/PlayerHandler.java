package com.staxter.talkingplayers.server.infrastructure.client;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import com.staxter.talkingplayers.shared.dto.command.CloseCommandDto;
import com.staxter.talkingplayers.shared.dto.command.CommandDto;
import com.staxter.talkingplayers.shared.dto.command.HelpCommandDto;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.RequiredArgsConstructor;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@RequiredArgsConstructor
public class PlayerHandler implements Runnable {

    private final Socket socket;
    private final CommandInvoker commandInvoker;

    private Player player;

    @Override
    public void run() {
        try (var channel = new SocketChannel(new ObjectOutputStream(socket.getOutputStream()));
             var reader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {

            player = new Player(String.valueOf(socket.getPort()), channel, null);

            System.out.println("Player connected: " + player.getName());
            commandInvoker.processCommand(new ServerCommandDto("Welcome to the Chat!"), player);
            commandInvoker.processCommand(new HelpCommandDto(), player);

            Object object;
            while (true) {
                synchronized (this) {
                    if ((object = reader.readObject()) == null) {
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
        }
    }

    private void closePlayerSession() {
        commandInvoker.processCommand(new CloseCommandDto(), player);
    }

}
