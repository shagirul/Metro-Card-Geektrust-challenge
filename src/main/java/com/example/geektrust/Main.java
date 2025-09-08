package com.example.geektrust;

import com.example.geektrust.app.ConsoleRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

	if (args == null || args.length == 0) {
		System.err.println("Please pass input file path as the first argument.");
		System.exit(1);
	}
	new ConsoleRunner().run(args[0]);

    }
}
