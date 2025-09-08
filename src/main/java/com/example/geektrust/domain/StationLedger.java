package com.example.geektrust.domain;

import java.util.EnumMap;
import java.util.Map;

public class StationLedger {
    private static final int INITIAL_PASSENGER_COUNT = 0;
    private static final int ZERO_AMOUNT = 0;

    private final Station station;
    private int totalCollection = ZERO_AMOUNT;
    private int totalDiscountGiven = ZERO_AMOUNT;
    private final Map<PassengerType, Integer> passengerCounts = new EnumMap<>(PassengerType.class);


    public StationLedger(Station station) {
        this.station = station;
        for (PassengerType t : PassengerType.values()) {
            passengerCounts.put(t, INITIAL_PASSENGER_COUNT);
        }
    }


    public Station station() { return station; }
    public int totalCollection() { return totalCollection; }
    public int totalDiscountGiven() { return totalDiscountGiven; }


    public void recordPassenger(PassengerType type) {
        passengerCounts.put(type, passengerCounts.get(type) + 1);
    }


    public Map<PassengerType, Integer> passengerCountsView() { return new EnumMap<>(passengerCounts); }


    public void addCollection(int amount) { this.totalCollection += amount; }

    public void addDiscount(int amount) { this.totalDiscountGiven += amount; }

    public void recordJourney(Journey journey) {
        recordPassenger(journey.getType());
        addCollection(journey.getPayable());
        addDiscount(journey.getDiscount());
    }
}
