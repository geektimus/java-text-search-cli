package com.codingmaniacs.interviews.cli.search;

import com.codingmaniacs.interviews.cli.search.file.FileFilterer;
import com.codingmaniacs.interviews.cli.search.file.FileIndexer;

import java.io.File;
import java.util.List;
import java.util.Map;
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

        FileFilterer fileFilterer = new FileFilterer(args[0]);

        List<String> textFilesToBeProcessed = fileFilterer.getTextFileNames();
        if (textFilesToBeProcessed.isEmpty()) {
            System.out.println("Warning: The directory provided is empty or doesn't contain text files");
            return;
        }

        Map<String, List<String>> indexedContent = FileIndexer.getIndexedContents(textFilesToBeProcessed);
        if (indexedContent.isEmpty()) {
            System.out.println("Warning: Could not find any words to be indexed on these files");
            System.out.println(String.format("%1$-9s","Please make sure that those files are not empty"));
            textFilesToBeProcessed.forEach(f -> System.out.println(String.format("%1$-9s", f)));
            return;
        }

        System.out.println("Loaded " + indexedContent.size() + " words");

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
