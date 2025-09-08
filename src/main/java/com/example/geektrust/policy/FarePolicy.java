package com.example.geektrust.policy;


import com.example.geektrust.domain.PassengerType;

public interface FarePolicy {
    int fareFor(PassengerType type);
}
