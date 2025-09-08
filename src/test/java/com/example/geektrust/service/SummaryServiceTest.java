package com.example.geektrust.service;

import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;
import com.example.geektrust.repository.StationLedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SummaryServiceTest {

    private StationLedgerRepository repo;
    private SummaryService service;

    private StationLedger centralLedger;
    private StationLedger airportLedger;

    @BeforeEach
    void setUp() {
        repo = mock(StationLedgerRepository.class);
        service = new SummaryService(repo);

        centralLedger = mock(StationLedger.class);
        airportLedger = mock(StationLedger.class);

        when(repo.get(Station.CENTRAL)).thenReturn(centralLedger);
        when(repo.get(Station.AIRPORT)).thenReturn(airportLedger);
    }

    @Test
    void buildPrintable_returnsCentralAndAirportLedgers() {
        SummaryService.PrintableSummary summary = service.buildPrintable();

        List<StationLedger> ledgers = summary.ledgers();
        assertEquals(2, ledgers.size());
        assertEquals(centralLedger, ledgers.get(0));
        assertEquals(airportLedger, ledgers.get(1));
    }

    @Test
    void sortedPassengerCounts_filtersZerosAndSortsByCountThenName() {
        Map<PassengerType, Integer> counts = new HashMap<>();
        counts.put(PassengerType.ADULT, 2);
        counts.put(PassengerType.SENIOR_CITIZEN, 2);
        counts.put(PassengerType.KID, 0);

        when(centralLedger.passengerCountsView()).thenReturn(counts);

        SummaryService.PrintableSummary summary =
                new SummaryService.PrintableSummary(Arrays.asList(centralLedger));
        List<Map.Entry<PassengerType, Integer>> result = summary.sortedPassengerCounts(centralLedger);

        assertEquals(2, result.size());
        // Both have count 2, so sorted alphabetically by enum name
        assertEquals(PassengerType.ADULT, result.get(0).getKey());
        assertEquals(PassengerType.SENIOR_CITIZEN, result.get(1).getKey());
    }

    @Test
    void ledgers_isImmutable() {
        SummaryService.PrintableSummary summary =
                new SummaryService.PrintableSummary(Arrays.asList(centralLedger, airportLedger));

        List<StationLedger> ledgers = summary.ledgers();
        assertEquals(2, ledgers.size());
        assertEquals(centralLedger, ledgers.get(0));
        assertEquals(airportLedger, ledgers.get(1));

        assertThrows(UnsupportedOperationException.class, () -> {
            ledgers.add(mock(StationLedger.class));
        });
    }
}
