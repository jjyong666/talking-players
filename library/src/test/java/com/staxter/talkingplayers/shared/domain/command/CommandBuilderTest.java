package com.staxter.talkingplayers.shared.domain.command;

import com.staxter.talkingplayers.shared.dto.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

class CommandBuilderTest {

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void build_returnsEmpty_whenNullOrEmptyCommand(String command) {

        assertTrue(CommandBuilder.build(command).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/aa", "bb", "/zz"})
    void build_returnsUnknownCommand(String command) {

        var result = CommandBuilder.build(command).orElseThrow();

        assertTrue(result instanceof UnknownCommandMessage);
    }

    @Test
    void build_returnsRegisterCommand() {
        String command = "/register";
        String name = "name";

        var result = (RegisterCommandMessage) CommandBuilder.build(String.format("%s %s", command, name)).orElseThrow();

        assertEquals(command, result.getMessage());
        assertEquals(name, result.getName());
    }

    @Test
    void build_returnsRegisterCommand_invalid() {
        String command = "/register";

        assertTrue(CommandBuilder.build(command).isEmpty());
    }

    @Test
    void build_returnsMsgCommand() {
        String command = "/msg";
        String name = "name";
        String message = "sdsds sdsd sd sdsd sdsd";

        var result = (MsgCommandMessage) CommandBuilder.build(String.format("%s %s %s", command, name, message)).orElseThrow();

        assertEquals(command, result.getMessage());
        assertEquals(name, result.getName());
        assertEquals(message, result.getMessageToSend());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "bb"})
    void build_returnsMsgCommand_invalid(String args) {
        String command = "/msg";

        assertTrue(CommandBuilder.build(String.format("%s %s", command, args)).isEmpty());
    }

    @Test
    void build_returnsListCommand() {
        String command = "/list";

        var result = (ListCommandMessage) CommandBuilder.build(command).orElseThrow();

        assertEquals(command, result.getMessage());
    }

    @Test
    void build_returnsHelpCommand() {
        String command = "/help";

        var result = (HelpCommandMessage) CommandBuilder.build(command).orElseThrow();

        assertEquals(command, result.getMessage());
    }

    @Test
    void build_returnsCloseCommand() {
        String command = "/close";

        var result = (CloseCommandMessage) CommandBuilder.build(command).orElseThrow();

        assertEquals(command, result.getMessage());
    }

}
