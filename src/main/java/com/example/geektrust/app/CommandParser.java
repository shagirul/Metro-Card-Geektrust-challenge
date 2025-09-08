package com.example.geektrust.app;

import java.util.Arrays;
import java.util.List;

public final class CommandParser {
    private CommandParser() {}
    private static final int COMMAND_INDEX = 0;
    private static final int ARGS_START_INDEX = 1;

    private static final int EMPTY_LENGTH = 0;


    public static Command parse(String raw) {
        String[] tokens = raw.trim().split("\\s+");
        if (tokens.length == EMPTY_LENGTH || tokens[COMMAND_INDEX].isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }
        String name = tokens[COMMAND_INDEX].trim();
        List<String> args = Arrays.asList(Arrays.copyOfRange(tokens, ARGS_START_INDEX, tokens.length));
        return new Command(name, args);
    }
}
