package com.example.geektrust.app;

import java.util.Arrays;
import java.util.List;

public final class CommandParser {
    private CommandParser() {}


    public static Command parse(String raw) {
        String[] tokens = raw.trim().split("\\s+");
        if (tokens.length == 0 || tokens[0].isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }
        String name = tokens[0].trim();
        List<String> args = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));
        return new Command(name, args);
    }
}
