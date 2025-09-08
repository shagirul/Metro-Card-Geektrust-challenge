package com.example.geektrust.policy.impl;


import com.example.geektrust.policy.RechargePolicy;

public final class ServiceFeeRechargePolicy implements RechargePolicy {
    private static final int FEE_PERCENT = 2;
    private static final double PERCENT_DIVISOR = 100.0;
    private static final int NO_FEE = 0;


    @Override
    public int serviceFeeFor(int rechargeAmount) {
        if (rechargeAmount <= NO_FEE) return NO_FEE;

        return (int) Math.ceil(rechargeAmount * (FEE_PERCENT / PERCENT_DIVISOR));
    }
}
