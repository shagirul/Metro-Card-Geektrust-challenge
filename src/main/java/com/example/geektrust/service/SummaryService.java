package com.example.geektrust.service;



import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;
import com.example.geektrust.repository.StationLedgerRepository;

import java.util.*;
import java.util.stream.Collectors;

public class SummaryService {
    private static final int ZERO = 0;
    private final StationLedgerRepository ledgerRepository;


    public SummaryService(StationLedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }


    public PrintableSummary buildPrintable() {
        StationLedger central = ledgerRepository.get(Station.CENTRAL);
        StationLedger airport = ledgerRepository.get(Station.AIRPORT);
        return new PrintableSummary(Arrays.asList(central, airport));
    }


    public static class PrintableSummary {
        private final List<StationLedger> ledgers;


        PrintableSummary(List<StationLedger> ledgers) {
            this.ledgers = Collections.unmodifiableList(new ArrayList<>(ledgers));
        }


        public List<StationLedger> ledgers() { return ledgers; }


        public List<Map.Entry<PassengerType, Integer>> sortedPassengerCounts(StationLedger ledger) {
            Map<PassengerType, Integer> counts = ledger.passengerCountsView();
            return counts.entrySet().stream()
                    .filter(e -> e.getValue() > ZERO)
                    .sorted((a, b) -> {
                        int byCount = Integer.compare(b.getValue(), a.getValue());
                        if (byCount != ZERO) return byCount;
                        return a.getKey().name().compareTo(b.getKey().name());
                    })
                    .collect(Collectors.toList());
        }
    }
}
