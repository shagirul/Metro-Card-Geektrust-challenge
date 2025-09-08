package com.example.geektrust.domain;

import java.util.EnumMap;
import java.util.Map;

public class StationLedger {
    private final Station station;
    private int totalCollection;
    private int totalDiscountGiven;
    private final Map<PassengerType, Integer> passengerCounts = new EnumMap<>(PassengerType.class);


    public StationLedger(Station station) {
        this.station = station;
        for (PassengerType t : PassengerType.values()) {
            passengerCounts.put(t, 0);
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
}
