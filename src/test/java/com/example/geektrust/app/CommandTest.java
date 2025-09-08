package com.example.geektrust.app;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void constructor_storesNameAndArgsImmutably() {
        List<String> args = Arrays.asList("ARG1", "ARG2");
        Command cmd = new Command("TEST", args);

        assertEquals("TEST", cmd.getName());
        assertEquals(Arrays.asList("ARG1", "ARG2"), cmd.getArgs());

        // Ensure immutability
        assertThrows(UnsupportedOperationException.class, () -> {
            cmd.getArgs().add("NEW");
        });
    }
}

