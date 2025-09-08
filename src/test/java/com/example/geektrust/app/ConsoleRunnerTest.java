package com.example.geektrust.app;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;

public class ConsoleRunnerTest {
    @Test
    void run_withEmptyFile_doesNothing() throws Exception {
        File tempFile = Files.createTempFile("empty", ".txt").toFile();
        new ConsoleRunner().run(tempFile.getAbsolutePath()); // should not throw
    }

    @Test
    void run_withOnlyBlankLines_doesNothing() throws Exception {
        File tempFile = Files.createTempFile("blank", ".txt").toFile();
        try (PrintWriter out = new PrintWriter(new FileWriter(tempFile))) {
            out.println("   ");
            out.println("\t");
        }
        new ConsoleRunner().run(tempFile.getAbsolutePath()); // should not throw
    }
}
