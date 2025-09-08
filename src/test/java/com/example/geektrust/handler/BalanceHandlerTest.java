package com.example.geektrust.handler;

import com.example.geektrust.app.Command;
import com.example.geektrust.repository.InMemoryMetroCardRepository;
import com.example.geektrust.service.BalanceService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceHandlerTest {

    @Test
    void shouldInitializeBalance() {
        InMemoryMetroCardRepository repo = new InMemoryMetroCardRepository();
        BalanceService service = new BalanceService(repo);
        BalanceHandler handler = new BalanceHandler(service);

        Command command = new Command("BALANCE", Arrays.asList("MC1", "100"));
        handler.handle(command);

        assertEquals(100, repo.getOrThrow("MC1").balance());
    }
}
