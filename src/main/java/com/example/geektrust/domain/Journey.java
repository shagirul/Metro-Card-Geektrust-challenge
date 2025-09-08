package com.example.geektrust.domain;

public final class Journey {
    private final PassengerType type;
    private final Station from;
    private final int baseFare;
    private final int discount;
    private final int payable;

    public Journey(PassengerType type, Station from, int baseFare, int discount) {
        this.type = type;
        this.from = from;
        this.baseFare = baseFare;
        this.discount = discount;
        this.payable = baseFare - discount;
    }
    public PassengerType getType() {
        return type;
    }

    public Station getFrom() {
        return from;
    }

    public int getBaseFare() {
        return baseFare;
    }

    public int getDiscount() {
        return discount;
    }

    public int getPayable() {
        return payable;
    }


}

