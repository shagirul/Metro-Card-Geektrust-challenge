package com.example.geektrust;

import com.example.geektrust.app.ConsoleRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static final int INPUT_FILE_ARG_INDEX = 0;
	private static final int ERROR_EXIT_CODE = 1;
	private static final int ZERO = 0;

    public static void main(String[] args) throws Exception {

	if (args == null || args.length == 0) {
		System.err.println("Please pass input file path as the first argument.");
		System.exit(ERROR_EXIT_CODE);
	}
	new ConsoleRunner().run(args[INPUT_FILE_ARG_INDEX]);

    }
}
