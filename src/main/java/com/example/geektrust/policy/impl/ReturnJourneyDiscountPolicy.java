package com.example.geektrust.policy.impl;


import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.Station;
import com.example.geektrust.policy.DiscountPolicy;

public final class ReturnJourneyDiscountPolicy implements DiscountPolicy {
    private static final int RETURN_DISCOUNT_PERCENT = 50;


    @Override
    public int discountFor(MetroCard card, Station fromStation, int originalFare) {
        if (card.isReturnFrom(fromStation)) {
            return (originalFare * RETURN_DISCOUNT_PERCENT) / 100;
        }
        return 0;
    }
}
