package com.staxter.talkingplayers.server.infrastructure.client;

import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.SocketWrapper;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.server.infrastructure.command.invoker.CommandInvoker;
import com.staxter.talkingplayers.shared.dto.command.*;
import com.staxter.talkingplayers.shared.infrastructure.channel.SocketChannel;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerHandlerTest {

    @Mock
    private CommandInvoker commandInvoker;

    @Mock
    private SocketWrapper socket;

    private PlayerHandler handler;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        initMocks(this);

        handler = new PlayerHandler(socket, commandInvoker);
    }

    @Test
    void run_welcomesPlayer() {

        handler.run();

        verify(commandInvoker).processCommand(any(ServerCommandDto.class), any(Player.class));
    }

    @Test
    void run_showsHelp() {

        handler.run();

        verify(commandInvoker).processCommand(any(HelpCommandDto.class), any(Player.class));
    }

    @Test
    @SneakyThrows
    void run_shouldReadInput() {

        handler.run();

        verify(socket).readObject();
    }

    @Test
    @SneakyThrows
    void run_whenInputIsNullItShouldCloseConnection() {
        when(socket.readObject()).thenReturn(null);

        handler.run();

        verify(commandInvoker).processCommand(any(CloseCommandDto.class), any(Player.class));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("commands")
    void run_commandsShouldBeSentTo_commandInvoker(CommandDto command) {
        when(socket.readObject()).thenReturn(command);
        when(commandInvoker.processCommand(any(), any())).thenReturn(true);

        handler.run();

        verify(commandInvoker).processCommand(eq(command), any(Player.class));
    }

    private static Stream<CommandDto> commands() {
        return Stream.of(new ListCommandDto(), new HelpCommandDto(), new RegisterCommandDto("as"));
    }

    @Test
    @SneakyThrows
    void run_playerShouldBeBuilt() {
        when(socket.readObject()).thenReturn(new ListCommandDto());

        when(commandInvoker.processCommand(any(), any())).thenReturn(true);
        int port = 3456;
        when(socket.getPort()).thenReturn(port);
        SocketChannel channel = new SocketChannel(null);
        when(socket.getChannel()).thenReturn(channel);

        handler.run();

        var captor = ArgumentCaptor.forClass(Player.class);
        verify(commandInvoker, atLeastOnce()).processCommand(any(), captor.capture());
        var player = captor.getAllValues().get(0);
        assertEquals(String.valueOf(port), player.getName());
        assertEquals(channel, player.getChannel());
    }

}
