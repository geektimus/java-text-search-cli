package com.codingmaniacs.interviews.cli.search.file;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileFilterer {

    /**
     * Filters .txt and .md files on a given directory
     *
     * @param directoryName Directory name
     * @return a List of text files (.txt|.md) in directory
     */
    public static List<String> getTextFileNames(String directoryName) {
        File directory = new File(directoryName);

        List<File> filesLoaded = null;

        // TODO: Add a better way to filter several extensions.
        File[] filesInDirectory = directory
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".txt") || name.toLowerCase().endsWith(".md"));

        if (filesInDirectory != null) {
            filesLoaded = Arrays.stream(filesInDirectory).collect(Collectors.toList());
        }

        if (filesLoaded == null)
            return Collections.emptyList();
        else {
            return filesLoaded.stream().map(File::getPath).collect(Collectors.toList());
        }
    }
}
