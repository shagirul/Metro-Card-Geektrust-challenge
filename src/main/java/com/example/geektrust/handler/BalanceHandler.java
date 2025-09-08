package com.example.geektrust.handler;


import com.example.geektrust.app.Command;
import com.example.geektrust.service.BalanceService;

public final class BalanceHandler implements CommandHandler {
    private final BalanceService balanceService;

    private static final int CARD_ID_INDEX = 0;
    private static final int AMOUNT_INDEX = 1;


    public BalanceHandler(BalanceService balanceService) {
        this.balanceService = balanceService;
    }


    @Override
    public void handle(Command command) {
        String cardId = command.getArgs().get(CARD_ID_INDEX);
        int amount = Integer.parseInt(command.getArgs().get(AMOUNT_INDEX));
        balanceService.initBalance(cardId, amount);
    }
}
