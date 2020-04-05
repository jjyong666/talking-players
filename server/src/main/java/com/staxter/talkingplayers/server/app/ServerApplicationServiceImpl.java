package com.staxter.talkingplayers.server.app;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.domain.service.PlayerService;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

/**
 * The type Server application service.
 */
@RequiredArgsConstructor
public class ServerApplicationServiceImpl implements ServerApplicationService {

    private final PlayerService service;

    @Override
    public Player register(Player player, String name) {
        return service.register(player, name);
    }

    @Override
    public boolean exists(String name) {
        return service.exists(name);
    }

    @Override
    public void delete(Player player) {
        service.delete(player);
    }

    @Override
    public Set<String> getPlayersNames() {
        return service.getPlayersNames();
    }

    @Override
    public void sendMessage(Player from, String to, String message) {
        Optional<Player> other = service.find(to);
        if (other.isEmpty()) {
            receiveMessage(from, new ErrorMessage("Player doesn't exist " + to));
            return;
        }
        from.sendMessage(message, other.get());
    }

    @Override
    public void receiveMessage(Player player, MessageDto message) {
        System.out.println(String.format("Message -> %s: %s", player.getName(), message.toPrint()));
        player.receiveMessage(message);
    }

}
