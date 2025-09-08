package com.example.geektrust.domain;

public enum PassengerType {
    ADULT(Fare.BASE_FARE_ADULT),
    SENIOR_CITIZEN(Fare.BASE_FARE_SENIOR),
    KID(Fare.BASE_FARE_KID);


    private final int baseFare;


    PassengerType(int baseFare) { this.baseFare = baseFare; }


    public int baseFare() { return baseFare; }
    public static class Fare {
        public static final int BASE_FARE_ADULT = 200;
        public static final int BASE_FARE_SENIOR = 100;
        public static final int BASE_FARE_KID = 50;
        // could add CURRENCY = "INR" if you want clarity
    }
}
