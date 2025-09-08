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

public class CheckInService {
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



        int payable = baseFare - discount;


        ensureSufficientBalance(card, payable, from);


        card.debit(payable);
        if(discount>0){
            card.markJourneyFrom(null);
        }else{
            card.markJourneyFrom(from);
        }





        StationLedger ledger = ledgerRepo.get(from);
        ledger.recordPassenger(type);
        ledger.addCollection(payable);
        ledger.addDiscount(discount);
    }


    private void ensureSufficientBalance(MetroCard card, int required, Station atStation) {
        int missing = Math.max(0, required - card.balance());
        if (missing > 0) {
            int fee = rechargePolicy.serviceFeeFor(missing);
            card.credit(missing);
            ledgerRepo.get(atStation).addCollection(fee);

        }
    }
}
