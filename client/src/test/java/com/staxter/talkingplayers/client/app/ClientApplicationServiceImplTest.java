package com.staxter.talkingplayers.client.app;

import com.staxter.talkingplayers.shared.domain.Channel;
import com.staxter.talkingplayers.shared.dto.MessageDto;
import com.staxter.talkingplayers.shared.dto.OwnMessage;
import com.staxter.talkingplayers.shared.dto.PlayerMessage;
import com.staxter.talkingplayers.shared.dto.ServerMessage;
import com.staxter.talkingplayers.shared.dto.command.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ClientApplicationServiceImplTest {

    private ClientApplicationServiceImpl service;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        initMocks(this);

        service = new ClientApplicationServiceImpl();
    }

    @Test
    void sendCommand() {
        var command = new CloseCommandDto();
        var channel = mock(Channel.class);

        service.sendCommand(command, channel);

        verify(channel).sendMessage(command);
    }

    @Test
    void receiveMessage_returnsTrue_whenCloseCommand() {
        var message = new ServerMessage(CloseCommandDto.COMMAND);
        var channel = mock(Channel.class);

        boolean result = service.receiveMessage(message, channel);

        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("commands")
    void receiveMessage_returnsFalse_whenOtherMessages(MessageDto message) {
        var channel = mock(Channel.class);

        boolean result = service.receiveMessage(message, channel);

        assertFalse(result);
    }

    private static Stream<MessageDto> commands() {
        return Stream.of(new ServerMessage("asas"), new PlayerMessage("sdsd", "sdsd"), new OwnMessage("sdas"));
    }

    @Test
    void receiveMessage_repliesMessage_whenPlayerMessage() {
        String messageText = "asasa";
        String player = "asasasdf";
        var message = new PlayerMessage(messageText, player);
        var channel = mock(Channel.class);

         service.receiveMessage(message, channel);

        var captor = ArgumentCaptor.forClass(MessageCommandDto.class);
        verify(channel).sendMessage(captor.capture());
        var messageCommand = captor.getValue();
        assertEquals(player, messageCommand.getName());
        assertTrue(messageCommand.getMessage().contains(messageText));
    }

    @Test
    void receiveMessage_decoratesRepliedMessage_whenPlayerMessage() {
        String messageText = "asasa";
        var message = new PlayerMessage(messageText, "asasasdf");
        var channel = mock(Channel.class);

        service.receiveMessage(message, channel);

        verifyDecoratedMessage(channel, messageText, 0);

        service.receiveMessage(message, channel);

        verifyDecoratedMessage(channel, messageText, 1);

    }

    private void verifyDecoratedMessage(Channel channel, String messageText, int count) {
        var captor = ArgumentCaptor.forClass(MessageCommandDto.class);
        verify(channel, atLeastOnce()).sendMessage(captor.capture());
        var messageCommand = captor.getValue();
        assertEquals(String.format("%s %d", messageText, count), messageCommand.getMessage());
    }

}
