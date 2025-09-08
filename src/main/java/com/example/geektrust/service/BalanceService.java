package com.example.geektrust.service;


import com.example.geektrust.repository.MetroCardRepository;

public final class BalanceService {
    private final MetroCardRepository repo;


    public BalanceService(MetroCardRepository repo) { this.repo = repo; }


    public void initBalance(String number, int balance) {
        repo.upsert(number, balance);
    }
}
