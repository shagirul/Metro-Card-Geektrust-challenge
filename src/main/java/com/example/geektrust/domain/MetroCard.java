package com.example.geektrust.domain;

import com.example.geektrust.policy.RechargePolicy;

import java.util.Objects;

public class MetroCard {
    private static final int NO_MISSING_BALANCE = 0;
    private final String number;
    private int balance;

    private Station lastFromStation;


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

    public void payFare(int amount, RechargePolicy rechargePolicy, StationLedger ledger) {
        int missing = Math.max(NO_MISSING_BALANCE, amount - balance);
        if (missing > NO_MISSING_BALANCE) {
            int fee = rechargePolicy.serviceFeeFor(missing);
            credit(missing);
            ledger.addCollection(fee);
        }
        debit(amount);
    }


}
