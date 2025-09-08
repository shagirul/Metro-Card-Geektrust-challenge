package com.example.geektrust.view;


import com.example.geektrust.domain.StationLedger;
import com.example.geektrust.service.SummaryService;

public class SummaryPrinter {
    public void print(SummaryService.PrintableSummary summary) {
        for (StationLedger ledger : summary.ledgers()) {
            System.out.println("TOTAL_COLLECTION " + ledger.station() + " " + ledger.totalCollection() + " " + ledger.totalDiscountGiven());
            System.out.println("PASSENGER_TYPE_SUMMARY");
            summary.sortedPassengerCounts(ledger)
                    .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
        }
    }
}
