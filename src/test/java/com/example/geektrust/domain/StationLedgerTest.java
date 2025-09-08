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
    @Test
    @DisplayName("recordJourney should update passenger count, collection, and discount")
    void recordJourney_shouldUpdateAllFields() {
        StationLedger ledger = new StationLedger(Station.CENTRAL);
        Journey journey = new Journey(PassengerType.ADULT, Station.CENTRAL, 200, 50);

        ledger.recordJourney(journey);

        Map<PassengerType, Integer> counts = ledger.passengerCountsView();
        assertEquals(1, counts.get(PassengerType.ADULT));
        assertEquals(150, ledger.totalCollection());   // payable = 200 - 50
        assertEquals(50, ledger.totalDiscountGiven());
    }

    @Test
    @DisplayName("recordJourney should accumulate multiple journeys")
    void recordJourney_shouldAccumulateMultipleJourneys() {
        StationLedger ledger = new StationLedger(Station.AIRPORT);

        ledger.recordJourney(new Journey(PassengerType.ADULT, Station.AIRPORT, 100, 20)); // payable 80
        ledger.recordJourney(new Journey(PassengerType.KID, Station.AIRPORT, 50, 0));    // payable 50
        ledger.recordJourney(new Journey(PassengerType.ADULT, Station.AIRPORT, 200, 50));// payable 150

        Map<PassengerType, Integer> counts = ledger.passengerCountsView();
        assertEquals(2, counts.get(PassengerType.ADULT));
        assertEquals(1, counts.get(PassengerType.KID));

        assertEquals(280, ledger.totalCollection()); // 80 + 50 + 150
        assertEquals(70, ledger.totalDiscountGiven()); // 20 + 50
    }
}
