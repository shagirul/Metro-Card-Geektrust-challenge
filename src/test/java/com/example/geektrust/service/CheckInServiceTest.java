package com.example.geektrust.service;

import com.example.geektrust.domain.MetroCard;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.domain.StationLedger;
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
    void checkIn_noDiscount_sufficientBalance() {
        when(farePolicy.fareFor(PassengerType.ADULT)).thenReturn(200);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 200)).thenReturn(0);
        when(card.balance()).thenReturn(500);

        checkInService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);

        verify(card).debit(200);
        verify(card).markJourneyFrom(Station.CENTRAL);
        verify(ledger).recordPassenger(PassengerType.ADULT);
        verify(ledger).addCollection(200);
        verify(ledger).addDiscount(0);
        verifyNoMoreInteractions(rechargePolicy);
    }

    @Test
    void checkIn_withDiscount() {
        when(farePolicy.fareFor(PassengerType.ADULT)).thenReturn(200);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 200)).thenReturn(100);
        when(card.balance()).thenReturn(500);

        checkInService.checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);

        verify(card).debit(100);
        verify(card).markJourneyFrom(null);
        verify(ledger).recordPassenger(PassengerType.ADULT);
        verify(ledger).addCollection(100);
        verify(ledger).addDiscount(100);
    }

    @Test
    void checkIn_insufficientBalance_triggersRecharge() {
        when(farePolicy.fareFor(PassengerType.KID)).thenReturn(50);
        when(discountPolicy.discountFor(card, Station.CENTRAL, 50)).thenReturn(0);
        when(card.balance()).thenReturn(20);
        when(rechargePolicy.serviceFeeFor(30)).thenReturn(1);

        checkInService.checkIn("MC1", PassengerType.KID, Station.CENTRAL);

        verify(card).credit(30);
        verify(ledger).addCollection(1);
        verify(card).debit(50);
        verify(card).markJourneyFrom(Station.CENTRAL);
        verify(ledger).recordPassenger(PassengerType.KID);
        verify(ledger).addCollection(50);
        verify(ledger).addDiscount(0);
    }

    @Test
    void checkIn_exactBalance_noRecharge() {
        when(farePolicy.fareFor(PassengerType.SENIOR_CITIZEN)).thenReturn(100);
        when(discountPolicy.discountFor(card, Station.AIRPORT, 100)).thenReturn(0);
        when(card.balance()).thenReturn(100);

        checkInService.checkIn("MC1", PassengerType.SENIOR_CITIZEN, Station.AIRPORT);

        verify(card).debit(100);
        verify(card).markJourneyFrom(Station.AIRPORT);
        verify(ledger).recordPassenger(PassengerType.SENIOR_CITIZEN);
        verify(ledger).addCollection(100);
        verify(ledger).addDiscount(0);
        verifyNoInteractions(rechargePolicy);
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
