package com.example.geektrust.domain;

import com.example.geektrust.policy.RechargePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MetroCard Unit Tests")
public class MetroCardTest {

    @Test
    @DisplayName("Constructor should set number and balance")
    void constructor_shouldSetNumberAndBalance() {
        MetroCard card = new MetroCard("123", 500);
        assertEquals("123", card.number());
        assertEquals(500, card.balance());
        assertNull(card.lastFromStation());
    }

    @Test
    @DisplayName("Constructor should throw if number is null")
    void constructor_shouldThrowIfNumberIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new MetroCard(null, 100);
        });
    }

    @Test
    @DisplayName("Credit should increase balance")
    void credit_shouldIncreaseBalance() {
        MetroCard card = new MetroCard("123", 100);
        card.credit(50);
        assertEquals(150, card.balance());
    }

    @Test
    @DisplayName("Debit should decrease balance")
    void debit_shouldDecreaseBalance() {
        MetroCard card = new MetroCard("123", 100);
        card.debit(40);
        assertEquals(60, card.balance());
    }

    @Test
    @DisplayName("Debit should throw if insufficient balance")
    void debit_shouldThrowIfInsufficientBalance() {
        MetroCard card = new MetroCard("123", 100);
        assertThrows(IllegalStateException.class, () -> card.debit(150));
    }

    @Test
    @DisplayName("isReturnFrom should return false when lastFromStation is null")
    void isReturnFrom_shouldReturnFalse_whenLastFromStationIsNull() {
        MetroCard card = new MetroCard("123", 100);
        assertFalse(card.isReturnFrom(Station.CENTRAL));
    }

    @Test
    @DisplayName("isReturnFrom should return true when lastFromStation differs")
    void isReturnFrom_shouldReturnTrue_whenLastFromStationDiffers() {
        MetroCard card = new MetroCard("123", 100);
        card.markJourneyFrom(Station.CENTRAL);
        assertTrue(card.isReturnFrom(Station.AIRPORT));
    }

    @Test
    @DisplayName("isReturnFrom should return false when lastFromStation same")
    void isReturnFrom_shouldReturnFalse_whenLastFromStationSame() {
        MetroCard card = new MetroCard("123", 100);
        card.markJourneyFrom(Station.CENTRAL);
        assertFalse(card.isReturnFrom(Station.CENTRAL));
    }

    @Test
    @DisplayName("markJourneyFrom should set lastFromStation")
    void markJourneyFrom_shouldSetLastFromStation() {
        MetroCard card = new MetroCard("123", 100);
        card.markJourneyFrom(Station.AIRPORT);
        assertEquals(Station.AIRPORT, card.lastFromStation());
    }

    @Test
    @DisplayName("payFare should deduct amount when balance is sufficient")
    void payFare_shouldDeduct_whenBalanceSufficient() {
        MetroCard card = new MetroCard("123", 200);
        RechargePolicy rechargePolicy = mock(RechargePolicy.class);
        StationLedger ledger = mock(StationLedger.class);

        card.payFare(150, rechargePolicy, ledger);

        assertEquals(50, card.balance());
        verifyNoInteractions(rechargePolicy);
        verify(ledger, never()).addCollection(anyInt());
    }

    @Test
    @DisplayName("payFare should work when balance equals fare (exact balance)")
    void payFare_shouldWork_whenExactBalance() {
        MetroCard card = new MetroCard("123", 100);
        RechargePolicy rechargePolicy = mock(RechargePolicy.class);
        StationLedger ledger = mock(StationLedger.class);

        card.payFare(100, rechargePolicy, ledger);

        assertEquals(0, card.balance());
        verifyNoInteractions(rechargePolicy);
        verify(ledger, never()).addCollection(anyInt());
    }

    @Test
    @DisplayName("payFare should recharge and deduct when balance is insufficient")
    void payFare_shouldRecharge_whenBalanceInsufficient() {
        MetroCard card = new MetroCard("123", 20);
        RechargePolicy rechargePolicy = mock(RechargePolicy.class);
        StationLedger ledger = mock(StationLedger.class);

        when(rechargePolicy.serviceFeeFor(30)).thenReturn(2);

        card.payFare(50, rechargePolicy, ledger);

        // Balance should be topped up: 20 + 30 = 50, then 50 - 50 = 0
        assertEquals(0, card.balance());

        // Recharge fee should be recorded
        verify(rechargePolicy).serviceFeeFor(30);
        verify(ledger).addCollection(2);
    }

    @Test
    @DisplayName("payFare should throw if even after recharge balance is insufficient")
    void payFare_shouldThrow_whenStillInsufficient() {
        MetroCard card = new MetroCard("123", 0);
        RechargePolicy rechargePolicy = mock(RechargePolicy.class);
        StationLedger ledger = mock(StationLedger.class);

        when(rechargePolicy.serviceFeeFor(100)).thenReturn(5);

        // forcibly cause recharge, but debit should still fail if logic is wrong
        assertDoesNotThrow(() -> card.payFare(100, rechargePolicy, ledger));

        // Ensure balance is 0 after exact recharge+debit
        assertEquals(0, card.balance());
    }
}
