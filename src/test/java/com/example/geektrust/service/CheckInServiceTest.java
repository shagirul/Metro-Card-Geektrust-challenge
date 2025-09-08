package com.example.geektrust.service;

import com.example.geektrust.domain.*;
import com.example.geektrust.policy.DiscountPolicy;
import com.example.geektrust.policy.FarePolicy;
import com.example.geektrust.policy.RechargePolicy;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationLedgerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CheckInServiceTest {

    private MetroCardRepository cardRepo;
    private StationLedgerRepository ledgerRepo;
    private FarePolicy farePolicy;
    private DiscountPolicy discountPolicy;
    private RechargePolicy rechargePolicy;
    private CheckInService checkInService;

    private MetroCard card;
    private StationLedger ledger;

    @BeforeEach
    void setUp() {
        cardRepo = mock(MetroCardRepository.class);
        ledgerRepo = mock(StationLedgerRepository.class);
        farePolicy = mock(FarePolicy.class);
        discountPolicy = mock(DiscountPolicy.class);
        rechargePolicy = mock(RechargePolicy.class);

        checkInService = new CheckInService(cardRepo, ledgerRepo, farePolicy, discountPolicy, rechargePolicy);

        card = mock(MetroCard.class);
        ledger = mock(StationLedger.class);

        when(cardRepo.getOrThrow("MC1")).thenReturn(card);
        when(ledgerRepo.get(any())).thenReturn(ledger);
    }

    @Test
    void checkIn_noDiscount() {
        when(farePolicy.fareFor(PassengerType.ADULT)).thenReturn(200);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 200)).thenReturn(0);

        checkInService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);

        verify(card).payFare(eq(200), eq(rechargePolicy), eq(ledger));
        ArgumentCaptor<Journey> captor = ArgumentCaptor.forClass(Journey.class);
        verify(ledger).recordJourney(captor.capture());

        Journey journey = captor.getValue();
        assertEquals(PassengerType.ADULT, journey.getType());
        assertEquals(200, journey.getBaseFare());
        assertEquals(0, journey.getDiscount());
        assertEquals(200, journey.getPayable());

        verify(card).markJourneyFrom(Station.CENTRAL);
    }

    @Test
    void checkIn_withDiscount() {
        when(farePolicy.fareFor(PassengerType.ADULT)).thenReturn(200);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 200)).thenReturn(50);

        checkInService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);

        verify(card).payFare(eq(150), eq(rechargePolicy), eq(ledger));

        ArgumentCaptor<Journey> captor = ArgumentCaptor.forClass(Journey.class);
        verify(ledger).recordJourney(captor.capture());
        assertEquals(50, captor.getValue().getDiscount());

        verify(card).markJourneyFrom(null);
    }

    @Test
    void ensureSufficientBalance_handlesNoMissing() throws Exception {
        when(farePolicy.fareFor(PassengerType.ADULT)).thenReturn(200);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 200)).thenReturn(0);
        when(card.balance()).thenReturn(200);

        checkInService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);


        verify(card, never()).credit(anyInt());
        verify(rechargePolicy, never()).serviceFeeFor(anyInt());
    }
}
