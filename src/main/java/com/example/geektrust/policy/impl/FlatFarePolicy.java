package com.example.geektrust.policy.impl;


import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.policy.FarePolicy;

public final class FlatFarePolicy implements FarePolicy {
    @Override
    public int fareFor(PassengerType type) {
        return type.baseFare();
    }
}
