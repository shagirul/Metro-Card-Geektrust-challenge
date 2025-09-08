package com.example.geektrust.handler;


import com.example.geektrust.app.Command;
import com.example.geektrust.service.BalanceService;

public final class BalanceHandler implements CommandHandler {
    private final BalanceService balanceService;


    public BalanceHandler(BalanceService balanceService) {
        this.balanceService = balanceService;
    }


    @Override
    public void handle(Command command) {
        String cardId = command.getArgs().get(0);
        int amount = Integer.parseInt(command.getArgs().get(1));
        balanceService.initBalance(cardId, amount);
    }
}
