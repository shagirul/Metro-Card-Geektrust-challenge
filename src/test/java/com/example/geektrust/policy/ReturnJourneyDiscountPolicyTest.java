package com.example.geektrust.policy;

import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.Station;
import com.example.geektrust.policy.impl.ReturnJourneyDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReturnJourneyDiscountPolicy Unit Tests")
public class ReturnJourneyDiscountPolicyTest {

    private final DiscountPolicy discountPolicy = new ReturnJourneyDiscountPolicy();

    @Test
    @DisplayName("discountFor returns 50% discount on return journey")
    void discountFor_returnJourney() {
        MetroCard card = new MetroCard("123", 1000);
        card.markJourneyFrom(Station.CENTRAL);

        int originalFare = 200;
        int discount = discountPolicy.discountFor(card, Station.AIRPORT, originalFare);
        assertEquals(originalFare / 2, discount);
    }

    @Test
    @DisplayName("discountFor returns 0 discount on first journey or same station")
    void discountFor_firstOrSameJourney() {
        MetroCard card = new MetroCard("456", 1000);
        int originalFare = 200;
        int discount = discountPolicy.discountFor(card, Station.CENTRAL, originalFare);
        assertEquals(0, discount);

        card.markJourneyFrom(Station.CENTRAL);
        discount = discountPolicy.discountFor(card, Station.CENTRAL, originalFare);
        assertEquals(0, discount);
    }
}
