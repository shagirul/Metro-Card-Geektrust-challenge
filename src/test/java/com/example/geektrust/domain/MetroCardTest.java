package com.example.geektrust.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
