package com.example.geektrust.policy;


import com.example.geektrust.policy.impl.ServiceFeeRechargePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ServiceFeeRechargePolicy Unit Tests")
public class ServiceFeeRechargePolicyTest {

    private final ServiceFeeRechargePolicy policy = new ServiceFeeRechargePolicy();

    @Test
    @DisplayName("serviceFeeFor returns 0 for zero recharge amount")
    void serviceFeeFor_zeroAmount_returnsZero() {
        assertEquals(0, policy.serviceFeeFor(0));
    }

    @Test
    @DisplayName("serviceFeeFor returns 0 for negative recharge amount")
    void serviceFeeFor_negativeAmount_returnsZero() {
        assertEquals(0, policy.serviceFeeFor(-100));
    }

    @Test
    @DisplayName("serviceFeeFor calculates correct fee rounded up for positive amounts")
    void serviceFeeFor_positiveAmount_calculatesCorrectFee() {
        // 2% of 100 = 2.0
        assertEquals(2, policy.serviceFeeFor(100));

        // 2% of 149 = 2.98, rounds up to 3
        assertEquals(3, policy.serviceFeeFor(149));

        // 2% of 1 = 0.02, rounds up to 1
        assertEquals(1, policy.serviceFeeFor(1));

        // 2% of 0.5 (integer input impossible, but test small positive integer)
        // Just test 1 again as minimum positive integer
        assertEquals(1, policy.serviceFeeFor(1));
    }
}
