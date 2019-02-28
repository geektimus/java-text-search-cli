package com.codingmaniacs.interviews.cli.search.file;

import com.codingmaniacs.interviews.cli.search.file.entities.IndexedWord;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileIndexer {

    private static final String CHARS_TO_BE_IGNORED = "[.,?!]";
    private static final String WORD_SEPARATOR = " ";

    private static final Map<String, Integer> wordCountPerFile = new HashMap<>(10);

    private FileIndexer() {

    }

    public static Map<String, String> getIndexedContents(String fileName) {

        final Path filePath = Paths.get(fileName);
        if (filePath == null || !filePath.toFile().exists()) {
            return Collections.emptyMap();
        }

        final Charset charset = Charset.forName("UTF-8");
        final String filePathString = filePath.toFile().getPath();

        try (Stream<String> stream = Files.lines(filePath, charset)) {
            Map<String, String> wordsIndexed = stream
                    .flatMap(FileIndexer::sanitizeAndTokenizeWords)
                    .distinct()
                    .map(w -> new IndexedWord(w, filePathString))
                    .collect(Collectors.toMap(IndexedWord::getWord, IndexedWord::getFileName));
            wordCountPerFile.put(fileName, wordsIndexed.size());
            return wordsIndexed;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return Collections.emptyMap();
    }

    private static Stream<String> sanitizeAndTokenizeWords(String textLine) {
        return Arrays
                .stream(
                        textLine
                                .toLowerCase()
                                .replaceAll(CHARS_TO_BE_IGNORED, "")
                                .split(WORD_SEPARATOR)
                );
    }

    public static Map<String, List<String>> getIndexedContents(List<String> fileNames) {
        return fileNames
                .stream()
                .flatMap(fn -> getIndexedContents(fn).entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    public static Map<String, Integer> getWordCountPerFile() {
        return wordCountPerFile;
    }
}
