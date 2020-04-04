package com.staxter.talkingplayers.server.infrastructure.factory;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.api.ServerControllerImpl;
import com.staxter.talkingplayers.server.app.ServerApplicationService;
import com.staxter.talkingplayers.server.app.ServerApplicationServiceImpl;
import com.staxter.talkingplayers.server.domain.config.MessageLimitConfig;
import com.staxter.talkingplayers.server.domain.manager.TalkManager;
import com.staxter.talkingplayers.server.domain.manager.TalkManagerMediator;
import com.staxter.talkingplayers.server.domain.repository.PlayerRepositoryInMemory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppFactory {

    public static ServerController buildPlayerController() {
        return new ServerControllerImpl(buildApplicationService());
    }

    private static ServerApplicationService buildApplicationService() {
        return new ServerApplicationServiceImpl(buildPlayerRepository(), buildTalkManager());
    }

    private static PlayerRepositoryInMemory buildPlayerRepository() {
        return new PlayerRepositoryInMemory();
    }

    private static TalkManager buildTalkManager() {
        return new TalkManagerMediator(buildMessageConfig());
    }

    private static MessageLimitConfig buildMessageConfig() {
        return new MessageLimitConfig();
    }
}
