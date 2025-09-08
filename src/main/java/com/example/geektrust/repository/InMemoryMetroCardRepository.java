package com.example.geektrust.repository;


import com.example.geektrust.domain.MetroCard;

import java.util.HashMap;
import java.util.Map;

public final class InMemoryMetroCardRepository implements MetroCardRepository {
    private final Map<String, MetroCard> store = new HashMap<>();


    @Override
    public MetroCard upsert(String cardNumber, int initialBalance) {
        return store.compute(cardNumber, (k, v) -> v == null ? new MetroCard(k, initialBalance) : new MetroCard(k, v.balance() + (initialBalance)));
    }


    @Override
    public MetroCard getOrThrow(String cardNumber) {
        MetroCard card = store.get(cardNumber);
        if (card == null) throw new IllegalArgumentException("Unknown card: " + cardNumber);
        return card;
    }

}
