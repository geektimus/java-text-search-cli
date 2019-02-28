package com.codingmaniacs.interviews.cli.search;

import java.io.File;
import java.util.Scanner;

public class App {

    private static boolean shouldBeRunning = true;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No directory given to index.");
            return;
        }

        final File indexableDirectory = new File(args[0]);

        if (!indexableDirectory.exists()) {
            System.out.println("Error: The directory " + args[0] + " doesn't exist");
            return;
        }

        // TODO: Index all files in indexableDirectory

        Scanner keyboard = new Scanner(System.in);

        while (shouldBeRunning) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            // TODO: Search indexed files for words in line

            if(!line.trim().isEmpty() && (line.equals(":quit") || line.equals(":exit"))) {
                shouldBeRunning = false;
            }
        }
    }
}
