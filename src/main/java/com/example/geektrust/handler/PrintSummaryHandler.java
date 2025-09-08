package com.example.geektrust.handler;


import com.example.geektrust.app.Command;
import com.example.geektrust.service.SummaryService;
import com.example.geektrust.view.SummaryPrinter;

public class PrintSummaryHandler implements CommandHandler {
    private final SummaryService summaryService;
    private final SummaryPrinter printer;


    public PrintSummaryHandler(SummaryService summaryService, SummaryPrinter printer) {
        this.summaryService = summaryService;
        this.printer = printer;
    }


    @Override
    public void handle(Command command) {
        printer.print(summaryService.buildPrintable());
    }
}
