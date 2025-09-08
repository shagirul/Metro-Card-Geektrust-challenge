package com.example.geektrust.policy;


import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.Station;

public interface DiscountPolicy {
    int discountFor(MetroCard card, Station fromStation, int originalFare);
}
