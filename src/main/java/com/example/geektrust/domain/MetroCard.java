package com.example.geektrust.domain;

import java.util.Objects;

public class MetroCard {
    private final String number;
    private int balance;
    // Remember last origin to determine return leg eligibility
    private Station lastFromStation; // null means no previous journey


    public MetroCard(String number, int initialBalance) {
        this.number = Objects.requireNonNull(number);
        this.balance = initialBalance;
    }


    public String number() { return number; }
    public int balance() { return balance; }
    public Station lastFromStation() { return lastFromStation; }


    public void credit(int amount) { this.balance += amount; }
    public void debit(int amount) {
        if (amount > balance) throw new IllegalStateException("Insufficient balance after recharge logic.");
        this.balance -= amount;
    }


    public boolean isReturnFrom(Station currentFrom) {
        return lastFromStation != null && lastFromStation != currentFrom;
    }


    public void markJourneyFrom(Station from) { this.lastFromStation = from; }

}
