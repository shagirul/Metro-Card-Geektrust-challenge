package com.example.geektrust.handler;


import com.example.geektrust.app.Command;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.service.CheckInService;

public final class CheckInHandler implements CommandHandler {
    private final CheckInService checkInService;

    private static final int CARD_ID_INDEX = 0;
    private static final int PASSENGER_TYPE_INDEX = 1;
    private static final int STATION_INDEX = 2;


    public CheckInHandler(CheckInService checkInService) {
        this.checkInService = checkInService;
    }


    @Override
    public void handle(Command command) {
        String cardId = command.getArgs().get(CARD_ID_INDEX);
        PassengerType type = PassengerType.valueOf(command.getArgs().get(PASSENGER_TYPE_INDEX));
        Station from = Station.valueOf(command.getArgs().get(STATION_INDEX));
        checkInService.checkIn(cardId, type, from);
    }
}
