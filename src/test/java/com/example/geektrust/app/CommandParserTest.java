package com.example.geektrust.app;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void parse_validCommand_parsesNameAndArgs() {
        Command cmd = CommandParser.parse("BALANCE MC1 600");

        assertEquals("BALANCE", cmd.getName());
        assertEquals(Arrays.asList("MC1", "600"), cmd.getArgs());
    }

    @Test
    void parse_trimsWhitespace() {
        Command cmd = CommandParser.parse("   CHECK_IN   MC1   ADULT   CENTRAL   ");

        assertEquals("CHECK_IN", cmd.getName());
        assertEquals(Arrays.asList("MC1", "ADULT", "CENTRAL"), cmd.getArgs());
    }

    @Test
    void parse_emptyString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> CommandParser.parse("   "));
    }
}

