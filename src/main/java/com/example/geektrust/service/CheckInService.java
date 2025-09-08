package com.example.geektrust.service;


import com.example.geektrust.domain.*;
import com.example.geektrust.policy.DiscountPolicy;
import com.example.geektrust.policy.FarePolicy;
import com.example.geektrust.policy.RechargePolicy;
import com.example.geektrust.repository.MetroCardRepository;
import com.example.geektrust.repository.StationLedgerRepository;

public class CheckInService {

    private static final int NO_DISCOUNT = 0;
    private static final int NO_MISSING_BALANCE = 0;
    private static final Station NO_STATION = null;


    private final MetroCardRepository cardRepo;
    private final StationLedgerRepository ledgerRepo;
    private final FarePolicy farePolicy;
    private final DiscountPolicy discountPolicy;
    private final RechargePolicy rechargePolicy;


    public CheckInService(MetroCardRepository cardRepo,
                          StationLedgerRepository ledgerRepo,
                          FarePolicy farePolicy,
                          DiscountPolicy discountPolicy,
                          RechargePolicy rechargePolicy) {
        this.cardRepo = cardRepo;
        this.ledgerRepo = ledgerRepo;
        this.farePolicy = farePolicy;
        this.discountPolicy = discountPolicy;
        this.rechargePolicy = rechargePolicy;
    }


    public void checkIn(String cardNumber, PassengerType type, Station from) {
        MetroCard card = cardRepo.getOrThrow(cardNumber);
        int baseFare = farePolicy.fareFor(type);
        int discount = discountPolicy.discountFor(card, from, baseFare);

        Journey journey = new Journey(type, from, baseFare, discount);

        StationLedger ledger = ledgerRepo.get(from);
        card.payFare(journey.getPayable(), rechargePolicy, ledger);
        if(discount>NO_DISCOUNT){
            card.markJourneyFrom(NO_STATION);
        }else{
            card.markJourneyFrom(from);
        }
        ledger.recordJourney(journey);
    }

}
