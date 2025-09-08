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
    public void run(String filePath) throws Exception {
        MetroCardRepository cardRepo = new InMemoryMetroCardRepository();
        StationLedgerRepository ledgerRepo = new InMemoryStationLedgerRepository();
        FarePolicy farePolicy = new FlatFarePolicy();
        DiscountPolicy discountPolicy = new ReturnJourneyDiscountPolicy();
        RechargePolicy rechargePolicy = new ServiceFeeRechargePolicy();


        BalanceService balanceService = new BalanceService(cardRepo);
        CheckInService checkInService = new CheckInService(cardRepo, ledgerRepo, farePolicy, discountPolicy, rechargePolicy);
        SummaryService summaryService = new SummaryService(ledgerRepo);
        SummaryPrinter printer = new SummaryPrinter();


        Map<String, CommandHandler> handlers = new HashMap<>();
        handlers.put("BALANCE", new BalanceHandler(balanceService));
        handlers.put("CHECK_IN", new CheckInHandler(checkInService));
        handlers.put("PRINT_SUMMARY", new PrintSummaryHandler(summaryService, printer));


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Command cmd = CommandParser.parse(line);
                CommandHandler handler = handlers.get(cmd.getName());
                if (handler == null) {
                    throw new IllegalArgumentException("Unknown command: " + cmd.getName());
                }
                handler.handle(cmd);
            }
        }
    }
}
