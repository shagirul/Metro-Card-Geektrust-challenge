package com.example.geektrust.policy;


import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.Station;

public interface DiscountPolicy {
    /**
     * @return discount in absolute units given the original fare
     */
    int discountFor(MetroCard card, Station fromStation, int originalFare);
}
