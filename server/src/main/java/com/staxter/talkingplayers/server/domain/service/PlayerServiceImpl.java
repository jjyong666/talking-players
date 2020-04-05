package com.staxter.talkingplayers.server.domain.service;

import com.staxter.talkingplayers.server.domain.manager.TalkManager;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.domain.repository.PlayerRepository;
import com.staxter.talkingplayers.shared.dto.ErrorMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;
    private final TalkManager manager;

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

        player.setName(name);
        player.setManager(manager);
        player = repository.save(player);
        player.receiveMessage(new ServerMessage("Welcome " + player.getName()));
        return player;
    }

    @Override
    public boolean exists(String name) {
        return find(name).isPresent();
    }

    @Override
    public Optional<Player> find(String name) {
        return repository.find(name);
    }

    @Override
    public void delete(Player player) {
        repository.delete(player.getName());
    }

    @Override
    public Set<String> getPlayersNames() {
        return repository.listPlayers();
    }

}
