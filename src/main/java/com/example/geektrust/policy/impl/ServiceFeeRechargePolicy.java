package com.example.geektrust.policy.impl;


import com.example.geektrust.policy.RechargePolicy;

public final class ServiceFeeRechargePolicy implements RechargePolicy {
    private static final int FEE_PERCENT = 2;


    @Override
    public int serviceFeeFor(int rechargeAmount) {
        if (rechargeAmount <= 0) return 0;
// Round up to nearest integer to avoid losing fractional currency units
        return (int) Math.ceil(rechargeAmount * (FEE_PERCENT / 100.0));
    }
}
