package com.example.geektrust.handler;


import com.example.geektrust.app.Command;

public interface CommandHandler {
    void handle(Command command);
}
