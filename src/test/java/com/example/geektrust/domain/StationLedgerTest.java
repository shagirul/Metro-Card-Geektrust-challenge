package com.example.geektrust.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StationLedger Unit Tests")
public class StationLedgerTest {

    @Test
    @DisplayName("Constructor initializes passenger counts with zero")
    void constructor_shouldInitializePassengerCountsWithZero() {
        StationLedger ledger = new StationLedger(Station.CENTRAL);
        assertEquals(Station.CENTRAL, ledger.station());
        Map<PassengerType, Integer> counts = ledger.passengerCountsView();
        for (PassengerType type : PassengerType.values()) {
            assertEquals(0, counts.get(type));
        }
    }

    @Test
    @DisplayName("recordPassenger should increment passenger count")
    void recordPassenger_shouldIncrementPassengerCount() {
        StationLedger ledger = new StationLedger(Station.AIRPORT);
        ledger.recordPassenger(PassengerType.ADULT);
        ledger.recordPassenger(PassengerType.ADULT);
        ledger.recordPassenger(PassengerType.KID);

        Map<PassengerType, Integer> counts = ledger.passengerCountsView();
        assertEquals(2, counts.get(PassengerType.ADULT));
        assertEquals(1, counts.get(PassengerType.KID));
        assertEquals(0, counts.get(PassengerType.SENIOR_CITIZEN));
    }

    @Test
    @DisplayName("addCollection should increase total collection")
    void addCollection_shouldIncreaseTotalCollection() {
        StationLedger ledger = new StationLedger(Station.CENTRAL);
        assertEquals(0, ledger.totalCollection());
        ledger.addCollection(100);
        ledger.addCollection(50);
        assertEquals(150, ledger.totalCollection());
    }

    @Test
    @DisplayName("addDiscount should increase total discount given")
    void addDiscount_shouldIncreaseTotalDiscountGiven() {
        StationLedger ledger = new StationLedger(Station.AIRPORT);
        assertEquals(0, ledger.totalDiscountGiven());
        ledger.addDiscount(30);
        ledger.addDiscount(20);
        assertEquals(50, ledger.totalDiscountGiven());
    }

    @Test
    @DisplayName("passengerCountsView returns a defensive copy")
    void passengerCountsView_shouldReturnDefensiveCopy() {
        StationLedger ledger = new StationLedger(Station.CENTRAL);
        ledger.recordPassenger(PassengerType.ADULT);
        Map<PassengerType, Integer> counts = ledger.passengerCountsView();


        counts.put(PassengerType.ADULT, 100);

        Map<PassengerType, Integer> countsAfterModification = ledger.passengerCountsView();
        assertEquals(1, countsAfterModification.get(PassengerType.ADULT));
    }
}
