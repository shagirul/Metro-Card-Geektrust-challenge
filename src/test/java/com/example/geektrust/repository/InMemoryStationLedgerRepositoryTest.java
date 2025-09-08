package com.example.geektrust.repository;


import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryStationLedgerRepository Unit Tests")
public class InMemoryStationLedgerRepositoryTest {

    @Test
    @DisplayName("Constructor initializes ledgers map for all stations")
    void constructor_initializesLedgersForAllStations() {
        InMemoryStationLedgerRepository repository = new InMemoryStationLedgerRepository();
        for (Station station : Station.values()) {
            StationLedger ledger = repository.get(station);
            assertNotNull(ledger, "Ledger should not be null for station: " + station);
            assertEquals(station, ledger.station(), "Ledger's station should match");
            assertEquals(0, ledger.totalCollection(), "Initial total collection should be zero");
            assertEquals(0, ledger.totalDiscountGiven(), "Initial total discount given should be zero");
        }
    }

    @Test
    @DisplayName("get returns the StationLedger for the given Station")
    void get_returnsStationLedgerForGivenStation() {
        InMemoryStationLedgerRepository repository = new InMemoryStationLedgerRepository();
        StationLedger centralLedger = repository.get(Station.CENTRAL);
        StationLedger airportLedger = repository.get(Station.AIRPORT);

        assertNotNull(centralLedger);
        assertEquals(Station.CENTRAL, centralLedger.station());

        assertNotNull(airportLedger);
        assertEquals(Station.AIRPORT, airportLedger.station());
    }
}
