package com.codingmaniacs.interviews.cli.search.file;

import com.codingmaniacs.interviews.cli.search.file.entities.IndexedWord;
import com.codingmaniacs.interviews.cli.search.text.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileIndexer {

    private FileIndexer() {

    }

    /**
     * Get the contents of a given file on a inverted index structure that maps words to file names.
     *
     * @param fileName File name
     * @return An inverted index structure Map(Word, FileName)
     */
    public static Map<String, String> getIndexedContents(String fileName) {

        final Path filePath = Paths.get(fileName);
        if (filePath == null || !filePath.toFile().exists() || filePath.toFile().isDirectory()) {
            return Collections.emptyMap();
        }

        final Charset charset = Charset.forName("UTF-8");
        final String filePathString = filePath.toFile().getPath();

        try (Stream<String> stream = Files.lines(filePath, charset)) {
            return stream
                    .flatMap(StringUtils::sanitizeAndTokenizeWords)
                    .distinct()
                    .map(w -> new IndexedWord(w, filePathString))
                    .collect(Collectors.toMap(IndexedWord::getWord, IndexedWord::getFileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return Collections.emptyMap();
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
}
