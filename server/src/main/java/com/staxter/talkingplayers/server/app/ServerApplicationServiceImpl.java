package com.staxter.talkingplayers.server.app;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.domain.repository.PlayerRepository;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Server application service.
 */
@RequiredArgsConstructor
public class ServerApplicationServiceImpl implements ServerApplicationService {

    private final PlayerRepository repository;

    @Override
    public Player register(Player player, String name) {
        if (exists(player.getName())) {
            player.receiveMessage(new ServerMessage("You are registered already!"));
            return player;
        }
        if (name == null || name.length() == 0) {
            player.receiveMessage(new ErrorMessage("Name is already taken"));
            return player;
        }
        if (exists(name)) {
            player.receiveMessage(new ErrorMessage("Player already exists!"));
            return player;
        }

        player = repository.save(player.setName(name));
        player.receiveMessage(new ServerMessage("Welcome " + player.getName()));
        return player;
    }

    @Override
    public boolean exists(String name) {
        return repository.find(name).isPresent();
    }

    @Override
    public void delete(Player player) {
        repository.delete(player.getName());
    }

    @Override
    public void sendMessage(Player from, String to, String message) {
        Optional<Player> other = repository.find(to);
        if (other.isEmpty()) {
            from.receiveMessage(new ErrorMessage("Player doesn't exist " + to));
            return;
        }
        from.sendMessage(message, other.get());
    }

    @Override
    public void listPlayers(Player player) {
        player.receiveMessage(
                new ServerMessage(
                        String.format("Players list: %s%s", System.lineSeparator(), getPlayers())));
    }

    private String getPlayers() {
        return repository.listPlayers()
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
