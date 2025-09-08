package com.example.geektrust.repository;


import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryMetroCardRepository Unit Tests")
public class InMemoryMetroCardRepositoryTest {

    @Test
    @DisplayName("upsert should add new MetroCard if not present")
    void upsert_shouldAddNewCard() {
        InMemoryMetroCardRepository repo = new InMemoryMetroCardRepository();
        MetroCard card = repo.upsert("card1", 500);
        assertNotNull(card);
        assertEquals("card1", card.number());
        assertEquals(500, card.balance());
    }

    @Test
    @DisplayName("upsert should add balance if MetroCard exists")
    void upsert_shouldAddBalanceForExistingCard() {
        InMemoryMetroCardRepository repo = new InMemoryMetroCardRepository();
        repo.upsert("card1", 500);
        MetroCard updatedCard = repo.upsert("card1", 300);
        assertEquals("card1", updatedCard.number());
        assertEquals(800, updatedCard.balance());
    }

    @Test
    @DisplayName("getOrThrow should return existing MetroCard")
    void getOrThrow_shouldReturnExistingCard() {
        InMemoryMetroCardRepository repo = new InMemoryMetroCardRepository();
        repo.upsert("card1", 200);
        MetroCard card = repo.getOrThrow("card1");
        assertEquals("card1", card.number());
        assertEquals(200, card.balance());
    }

    @Test
    @DisplayName("getOrThrow should throw for unknown card")
    void getOrThrow_shouldThrowForUnknownCard() {
        InMemoryMetroCardRepository repo = new InMemoryMetroCardRepository();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            repo.getOrThrow("nonExisting");
        });
        assertTrue(exception.getMessage().contains("Unknown card"));
    }
}
