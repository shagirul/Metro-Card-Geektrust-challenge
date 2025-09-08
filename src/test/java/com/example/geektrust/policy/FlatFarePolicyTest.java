package com.example.geektrust.policy;

import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.policy.impl.FlatFarePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FlatFarePolicy Unit Tests")
public class FlatFarePolicyTest {

    private final FlatFarePolicy policy = new FlatFarePolicy();

    @Test
    @DisplayName("fareFor returns correct fare for ADULT")
    void fareForAdult() {
        int fare = policy.fareFor(PassengerType.ADULT);
        assertEquals(200, fare);
    }

    @Test
    @DisplayName("fareFor returns correct fare for SENIOR_CITIZEN")
    void fareForSeniorCitizen() {
        int fare = policy.fareFor(PassengerType.SENIOR_CITIZEN);
        assertEquals(100, fare);
    }

    @Test
    @DisplayName("fareFor returns correct fare for KID")
    void fareForKid() {
        int fare = policy.fareFor(PassengerType.KID);
        assertEquals(50, fare);
    }
}

