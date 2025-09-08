package com.example.geektrust.repository;


import com.example.geektrust.domain.MetroCard;

public interface MetroCardRepository {
    MetroCard upsert(String cardNumber, int initialBalance);
    MetroCard getOrThrow(String cardNumber);


}
