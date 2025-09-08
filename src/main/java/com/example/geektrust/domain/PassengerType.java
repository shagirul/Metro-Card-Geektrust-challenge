package com.example.geektrust.domain;

public enum PassengerType {
    ADULT(200),
    SENIOR_CITIZEN(100),
    KID(50);


    private final int baseFare;


    PassengerType(int baseFare) { this.baseFare = baseFare; }


    public int baseFare() { return baseFare; }
}
