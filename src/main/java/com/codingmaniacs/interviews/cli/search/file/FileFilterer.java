package com.codingmaniacs.interviews.cli.search.file;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileFilterer {
    private List<File> filesLoaded;

    public FileFilterer(String directoryName) {
        initialize(directoryName);
    }

    private void initialize(String directoryName) {
        File directory = new File(directoryName);

        // TODO: Add a better way to filter several extensions.
        File[] filesInDirectory = directory
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".txt") || name.toLowerCase().endsWith(".md"));

        if (filesInDirectory != null) {
            filesLoaded = Arrays.stream(filesInDirectory).collect(Collectors.toList());
        }
    }

    public List<String> getTextFileNames() {
        if (filesLoaded == null)
            return Collections.emptyList();
        return filesLoaded.stream().map(File::getPath).collect(Collectors.toList());
    }
}
