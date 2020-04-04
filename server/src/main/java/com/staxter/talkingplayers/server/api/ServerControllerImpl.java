package com.staxter.talkingplayers.server.api;

import com.staxter.talkingplayers.server.app.ServerApplicationService;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.domain.Channel;
import lombok.RequiredArgsConstructor;

/**
 * The type Server controller.
 */
@RequiredArgsConstructor
public class ServerControllerImpl implements ServerController {

    private final ServerApplicationService service;

    @Override
    public Player registerPlayer(Player player, String name) {
        return service.register(player, name);
    }

    @Override
    public void listPlayers(Player player) {
        service.listPlayers(player);
    }

    @Override
    public boolean existsPlayer(String name) {
        return service.exists(name);
    }

    @Override
    public void sendMessage(Player player, String to, String message) {
        service.sendMessage(player, to, message);
    }

    @Override
    public void deletePlayer(Player player) {
        service.delete(player);
    }

    @Override
    public Player buildPlayer(String player, Channel channel) {
        return service.buildPlayer(player, channel);
    }

}
