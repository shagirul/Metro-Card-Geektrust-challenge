package com.example.geektrust.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Command {
    private final String name;
    private final List<String> args;


    public Command(String name, List<String> args) {
        this.name = name;
        this.args = Collections.unmodifiableList(new ArrayList<>(args));;
    }


    public String getName() { return name; }
    public List<String> getArgs() { return Collections.unmodifiableList(args); }
}
