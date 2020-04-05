package com.staxter.talkingplayers.server.api;

import com.staxter.talkingplayers.server.app.ServerApplicationService;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import lombok.RequiredArgsConstructor;

import java.util.Set;

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
    public Set<String> getPlayersNames() {
        return service.getPlayersNames();
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
    public void receiveMessage(Player player, MessageDto message) {
        service.receiveMessage(player, message);
    }

    @Override
    public void deletePlayer(Player player) {
        service.delete(player);
    }

}
