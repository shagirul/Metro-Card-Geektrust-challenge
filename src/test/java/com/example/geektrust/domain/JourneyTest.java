package com.example.geektrust.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Journey Unit Tests")
class JourneyTest {

    @Test
    @DisplayName("Constructor should set all fields correctly")
    void constructor_shouldSetAllFields() {
        Journey journey = new Journey(PassengerType.ADULT, Station.CENTRAL, 200, 50);

        assertEquals(PassengerType.ADULT, journey.getType());
        assertEquals(Station.CENTRAL, journey.getFrom());
        assertEquals(200, journey.getBaseFare());
        assertEquals(50, journey.getDiscount());
        assertEquals(150, journey.getPayable());
    }

    @Test
    @DisplayName("Payable should equal baseFare when discount is zero")
    void payable_shouldEqualBaseFare_whenNoDiscount() {
        Journey journey = new Journey(PassengerType.KID, Station.AIRPORT, 100, 0);

        assertEquals(100, journey.getBaseFare());
        assertEquals(0, journey.getDiscount());
        assertEquals(100, journey.getPayable());
    }

    @Test
    @DisplayName("Payable should be zero when discount equals baseFare")
    void payable_shouldBeZero_whenFullDiscount() {
        Journey journey = new Journey(PassengerType.SENIOR_CITIZEN, Station.CENTRAL, 120, 120);

        assertEquals(120, journey.getBaseFare());
        assertEquals(120, journey.getDiscount());
        assertEquals(0, journey.getPayable());
    }

    @Test
    @DisplayName("Supports different passenger types and stations")
    void supportsDifferentPassengerTypesAndStations() {
        Journey journey1 = new Journey(PassengerType.ADULT, Station.CENTRAL, 150, 20);
        Journey journey2 = new Journey(PassengerType.KID, Station.AIRPORT, 80, 10);

        assertEquals(PassengerType.ADULT, journey1.getType());
        assertEquals(Station.CENTRAL, journey1.getFrom());
        assertEquals(130, journey1.getPayable());

        assertEquals(PassengerType.KID, journey2.getType());
        assertEquals(Station.AIRPORT, journey2.getFrom());
        assertEquals(70, journey2.getPayable());
    }
}
