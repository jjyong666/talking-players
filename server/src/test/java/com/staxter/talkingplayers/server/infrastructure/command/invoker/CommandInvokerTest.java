package com.staxter.talkingplayers.server.infrastructure.command.invoker;

import com.staxter.talkingplayers.server.api.ServerController;
import com.staxter.talkingplayers.server.domain.model.Player;
import com.staxter.talkingplayers.server.infrastructure.command.dto.ServerCommandDto;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class CommandInvokerTest {

    private static final Player PLAYER = new Player("name", null, null);

    @Mock
    private ServerController controller;

    private CommandInvoker commandInvoker;

    @BeforeEach
    void setUp() {
        initMocks(this);

        commandInvoker = new CommandInvoker(controller);
    }

    @Test
    void processCommand_close() {

        commandInvoker.processCommand(new CloseCommandDto(), PLAYER);

        verify(controller).deletePlayer(PLAYER);
    }

    @Test
    void processCommand_close_returnsTrue() {

        var result = commandInvoker.processCommand(new CloseCommandDto(), PLAYER);

        assertTrue(result);
    }

    @Test
    void processCommand_help() {

        commandInvoker.processCommand(new HelpCommandDto(), PLAYER);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(eq(PLAYER), captor.capture());
        var message = captor.getValue().getMessage();

        assertAll(
                () -> assertTrue(message.contains(RegisterCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(MessageCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(ListCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(HelpCommandDto.DESCRIPTION)),
                () -> assertTrue(message.contains(CloseCommandDto.DESCRIPTION))
        );
    }

    @Test
    void processCommand_help_returnsFalse() {

        var result = commandInvoker.processCommand(new HelpCommandDto(), PLAYER);

        assertFalse(result);
    }

    @Test
    void processCommand_list_checksPlayerIsRegistered() {

        commandInvoker.processCommand(new ListCommandDto(), PLAYER);

        verify(controller).existsPlayer(PLAYER.getName());
    }

    @Test
    void processCommand_list_ifRegisteredReturnNames() {
        var command = new ListCommandDto();
        String name1 = "name1";
        String name2 = "name2";
        mockPlayerIsRegistered(true);
        mockExistingPlayers(Arrays.asList(name1, name2));

        commandInvoker.processCommand(command, PLAYER);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(eq(PLAYER), captor.capture());
        var message = captor.getValue().getMessage();

        assertAll(
                () -> assertTrue(message.contains(name1)),
                () -> assertTrue(message.contains(name2))
        );
    }

    private void mockPlayerIsRegistered(boolean isRegistered) {
        when(controller.existsPlayer(anyString())).thenReturn(isRegistered);
    }

    private void mockExistingPlayers(List<String> players) {
        when(controller.getPlayersNames()).thenReturn(new HashSet<>(players));
    }

    @Test
    void processCommand_list_ifNotRegisteredDontReturnNames() {
        var command = new ListCommandDto();
        String name1 = "name1";
        String name2 = "name2";
        mockPlayerIsRegistered(false);
        mockExistingPlayers(Arrays.asList(name1, name2));

        commandInvoker.processCommand(command, PLAYER);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(eq(PLAYER), captor.capture());
        var message = captor.getValue().getMessage();

        assertAll(
                () -> assertFalse(message.contains(name1)),
                () -> assertFalse(message.contains(name2))
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void processCommand_list_returnsFalse(boolean isRegistered) {
        mockPlayerIsRegistered(isRegistered);

        var result = commandInvoker.processCommand(new ListCommandDto(), PLAYER);

        assertFalse(result);
    }

    @Test
    void processCommand_receiveMessage() {
        String message = "asasass";

        commandInvoker.processCommand(new ServerCommandDto(message), PLAYER);

        var captor = ArgumentCaptor.forClass(ServerMessage.class);
        verify(controller).receiveMessage(eq(PLAYER), captor.capture());
        var result = captor.getValue().getMessage();

        assertEquals(message, result);
    }

    @Test
    void processCommand_receiveMessage_returnsFalse() {

        var result = commandInvoker.processCommand(new ServerCommandDto("message"), PLAYER);

        assertFalse(result);
    }

    @Test
    void processCommand_register() {
        String name = "asasass";

        commandInvoker.processCommand(new RegisterCommandDto(name), PLAYER);

        verify(controller).registerPlayer(PLAYER, name);
    }

    @Test
    void processCommand_register_returnsFalse() {

        var result = commandInvoker.processCommand(new RegisterCommandDto("name"), PLAYER);

        assertFalse(result);
    }

    @Test
    void processCommand_sendMessage_checksPlayerIsRegistered() {

        commandInvoker.processCommand(new MessageCommandDto("name", "message"), PLAYER);

        verify(controller).existsPlayer(PLAYER.getName());
    }

    @Test
    void processCommand_sendMessage_whenRegistered() {
        String name = "asasass";
        String message = "sfssfsffds";
        mockPlayerIsRegistered(true);

        commandInvoker.processCommand(new MessageCommandDto(name, message), PLAYER);

        verify(controller).sendMessage(PLAYER, name, message);
    }

    @Test
    void processCommand_sendMessage_whenNotRegistered() {
        String name = "asasass";
        String message = "sfssfsffds";
        mockPlayerIsRegistered(false);

        commandInvoker.processCommand(new MessageCommandDto(name, message), PLAYER);

        verify(controller, never()).sendMessage(PLAYER, name, message);
        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void processCommand_sendMessage_returnsFalse(boolean isRegistered) {
        mockPlayerIsRegistered(isRegistered);

        var result = commandInvoker.processCommand(new MessageCommandDto("name", "message"), PLAYER);

        assertFalse(result);
    }

    @Test
    void processCommand_nullCommand_returnsFalse() {

        var result = commandInvoker.processCommand(null, PLAYER);

        assertFalse(result);
        verifyNoInteractions(controller);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void processCommand_nullCommand_command_returnsFalse(String cmd) {
        var command = mock(CommandDto.class);
        when(command.getCommand()).thenReturn(cmd);

        var result = commandInvoker.processCommand(command, PLAYER);

        assertFalse(result);
        verifyNoInteractions(controller);
    }

    @Test
    void processCommand_notRegisteredCommand_returnsFalse() {

        var result = commandInvoker.processCommand(new UnknownCommandDto("aa"), PLAYER);

        assertFalse(result);
        verify(controller).receiveMessage(eq(PLAYER), any(ServerMessage.class));
    }

}
