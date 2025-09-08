package com.example.geektrust.service;

import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.repository.MetroCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("BalanceService Unit Tests With Mockito")
public class BalanceServiceTest {

    @Mock
    private MetroCardRepository mockRepo;

    @InjectMocks
    private BalanceService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("initBalance should call upsert on repository with correct arguments")
    void initBalance_callsRepositoryUpsert() {
        String cardNumber = "MC123";
        int balance = 500;

        MetroCard mockCard = new MetroCard(cardNumber, balance);

        when(mockRepo.upsert(cardNumber, balance)).thenReturn(mockCard);

        service.initBalance(cardNumber, balance);

        verify(mockRepo, times(1)).upsert(cardNumber, balance);
    }
}
