package com.example.geektrust.domain;

public enum Station {
    CENTRAL,
    AIRPORT;


    public Station otherEnd() {
        return this == CENTRAL ? AIRPORT : CENTRAL;
    }
}
