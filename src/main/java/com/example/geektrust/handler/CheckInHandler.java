package com.example.geektrust.handler;


import com.example.geektrust.app.Command;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.service.CheckInService;

public final class CheckInHandler implements CommandHandler {
    private final CheckInService checkInService;


    public CheckInHandler(CheckInService checkInService) {
        this.checkInService = checkInService;
    }


    @Override
    public void handle(Command command) {
// CHECK_IN <METROCARD_NUMBER> <PASSENGER_TYPE> <FROM_STATION>
        String cardId = command.getArgs().get(0);
        PassengerType type = PassengerType.valueOf(command.getArgs().get(1));
        Station from = Station.valueOf(command.getArgs().get(2));
        checkInService.checkIn(cardId, type, from);
    }
}
