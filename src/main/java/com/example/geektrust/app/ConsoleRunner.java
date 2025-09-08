package com.example.geektrust.app;

import com.example.geektrust.handler.BalanceHandler;
import com.example.geektrust.handler.CheckInHandler;
import com.example.geektrust.handler.CommandHandler;
import com.example.geektrust.handler.PrintSummaryHandler;
import com.example.geektrust.policy.DiscountPolicy;
import com.example.geektrust.policy.FarePolicy;
import com.example.geektrust.policy.RechargePolicy;
import com.example.geektrust.policy.impl.FlatFarePolicy;
import com.example.geektrust.policy.impl.ReturnJourneyDiscountPolicy;
import com.example.geektrust.policy.impl.ServiceFeeRechargePolicy;
import com.example.geektrust.repository.InMemoryMetroCardRepository;
import com.example.geektrust.repository.InMemoryStationLedgerRepository;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationLedgerRepository;
import com.example.geektrust.service.BalanceService;
import com.example.geektrust.service.CheckInService;
import com.example.geektrust.service.SummaryService;
import com.example.geektrust.view.SummaryPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ConsoleRunner {
    private final Map<String, CommandHandler> handlers;

    public ConsoleRunner() {
        this.handlers = createHandlers();
    }

    // Separate method to compose dependencies and handlers
    private Map<String, CommandHandler> createHandlers() {
        MetroCardRepository cardRepo = new InMemoryMetroCardRepository();
        StationLedgerRepository ledgerRepo = new InMemoryStationLedgerRepository();
        FarePolicy farePolicy = new FlatFarePolicy();
        DiscountPolicy discountPolicy = new ReturnJourneyDiscountPolicy();
        RechargePolicy rechargePolicy = new ServiceFeeRechargePolicy();

        BalanceService balanceService = new BalanceService(cardRepo);
        CheckInService checkInService = new CheckInService(cardRepo, ledgerRepo, farePolicy, discountPolicy, rechargePolicy);
        SummaryService summaryService = new SummaryService(ledgerRepo);
        SummaryPrinter printer = new SummaryPrinter();

        Map<String, CommandHandler> map = new HashMap<>();
        map.put("BALANCE", new BalanceHandler(balanceService));
        map.put("CHECK_IN", new CheckInHandler(checkInService));
        map.put("PRINT_SUMMARY", new PrintSummaryHandler(summaryService, printer));
        return map;
    }

    public void run(String filePath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(CommandParser::parse)
                    .forEach(this::dispatch);
        }
    }

    private void dispatch(Command command) {
        CommandHandler handler = handlers.get(command.getName());
        if (handler == null) {
            throw new IllegalArgumentException("Unknown command: " + command.getName());
        }
        handler.handle(command);
    }
}
