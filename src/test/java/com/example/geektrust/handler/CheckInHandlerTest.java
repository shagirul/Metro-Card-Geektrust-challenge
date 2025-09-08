package com.example.geektrust.handler;

import com.example.geektrust.app.Command;
import com.example.geektrust.domain.PassengerType;
import com.example.geektrust.domain.Station;
import com.example.geektrust.service.CheckInService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class CheckInHandlerTest {

    @Test
    void shouldDelegateToCheckInService() {
        CheckInService mockService = mock(CheckInService.class);
        CheckInHandler handler = new CheckInHandler(mockService);

        Command command = new Command("CHECK_IN",
                Arrays.asList("MC1", PassengerType.ADULT.name(), Station.CENTRAL.name()));

        handler.handle(command);

        verify(mockService, times(1))
                .checkIn("MC1", PassengerType.ADULT, Station.CENTRAL);
    }
}
