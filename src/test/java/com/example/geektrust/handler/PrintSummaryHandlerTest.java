package com.example.geektrust.handler;

import com.example.geektrust.app.Command;
import com.example.geektrust.service.SummaryService;
import com.example.geektrust.view.SummaryPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.*;

class PrintSummaryHandlerTest {

    @Mock
    private SummaryService summaryService;

    @Mock
    private SummaryPrinter printer;

    private PrintSummaryHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new PrintSummaryHandler(summaryService, printer);
    }

    @Test
    void shouldDelegateToSummaryPrinter() {
        SummaryService.PrintableSummary fakeSummary = mock(SummaryService.PrintableSummary.class);
        when(summaryService.buildPrintable()).thenReturn(fakeSummary);

        Command command = new Command("PRINT_SUMMARY", Collections.emptyList());

        handler.handle(command);

        verify(summaryService).buildPrintable();
        verify(printer).print(fakeSummary);
    }

}
