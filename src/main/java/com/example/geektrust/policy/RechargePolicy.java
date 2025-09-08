package com.example.geektrust.policy;

public interface RechargePolicy {
    /**
     * Calculates the service fee charged on top of the recharge amount (which is just enough to cover the fare).
     * @param rechargeAmount amount credited to the card (positive int)
     * @return fee charged and counted into station collection (non-negative int)
     */
    int serviceFeeFor(int rechargeAmount);
}
