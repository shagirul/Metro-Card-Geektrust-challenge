package com.example.geektrust.view;

import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;
import com.example.geektrust.service.SummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SummaryPrinterTest {

    private SummaryPrinter printer;
    private SummaryService.PrintableSummary summary;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        printer = new SummaryPrinter();
        System.setOut(new PrintStream(outContent));

        summary = mock(SummaryService.PrintableSummary.class);
    }

    @Test
    void shouldPrintLedgerAndPassengerSummary() {
        // Arrange
        StationLedger ledger = mock(StationLedger.class);
        when(ledger.station()).thenReturn(Station.CENTRAL);
        when(ledger.totalCollection()).thenReturn(100);
        when(ledger.totalDiscountGiven()).thenReturn(10);

        when(summary.ledgers()).thenReturn(Arrays.asList(ledger));

        Map<PassengerType, Integer> passengerCounts = new HashMap<>();
        passengerCounts.put(PassengerType.ADULT, 2);
        passengerCounts.put(PassengerType.KID, 1);

        List<Map.Entry<PassengerType, Integer>> passengerList = new ArrayList<>(passengerCounts.entrySet());
        when(summary.sortedPassengerCounts(ledger)).thenReturn(passengerList);

        printer.print(summary);

        String output = outContent.toString();

        assertTrue(output.contains("TOTAL_COLLECTION CENTRAL 100 10"));
        assertTrue(output.contains("PASSENGER_TYPE_SUMMARY"));
        assertTrue(output.contains("ADULT 2"));
        assertTrue(output.contains("KID 1"));

        System.setOut(originalOut);
    }


}
