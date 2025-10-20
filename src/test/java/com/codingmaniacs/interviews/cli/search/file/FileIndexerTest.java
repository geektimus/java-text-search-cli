package com.codingmaniacs.interviews.cli.search.file;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileIndexerTest {

    @Test
    public void testNonExistentFileIndexer() {
        String nonExistentFilePath = "fake-file.txt";

        Map<String, String> wordsInFile = FileIndexer.getIndexedContents(nonExistentFilePath);

        assertEquals("The indexer should return 0 for a non existent file", 0, wordsInFile.size());
    }

    @Test
    public void testSimpleFileIndexer() {
        String textFilePath = "src/test/resources/fake-directory/a-text-file.txt";

        Map<String, String> wordsInFile = FileIndexer.getIndexedContents(textFilePath);

        assertEquals("The indexer should return the number of words on a file", 63, wordsInFile.size());
        assertEquals("The file name should be the same used for the test", textFilePath.replace("\\", "/"), wordsInFile.get("lorem").replace("\\", "/"));

        textFilePath = "src/test/resources/fake-directory/b-text-file.txt";
        wordsInFile = FileIndexer.getIndexedContents(textFilePath);

        assertEquals("The indexer should return the number of words on a file", 70, wordsInFile.size());
        assertEquals("The file name should be the same used for the test", textFilePath.replace("\\", "/"), wordsInFile.get("voluptatem").replace("\\", "/"));
    }

    @Test
    public void testMultipleFileIndexer() {
        List<String> textFilePaths = Arrays.asList(
                "src/test/resources/fake-directory/a-text-file.txt",
                "src/test/resources/fake-directory/b-text-file.txt"
        );

        Map<String, List<String>> wordsInFiles = FileIndexer.getIndexedContents(textFilePaths);

        assertEquals(
                "The indexer should return the number of words on a file",
                117,
                wordsInFiles.size());
        List<String> expectedPaths = textFilePaths.stream()
                .map(path -> path.replace("\\", "/"))
                .collect(java.util.stream.Collectors.toList());
        List<String> actualPaths = wordsInFiles.get("dolor").stream()
                .map(path -> path.replace("\\", "/"))
                .collect(java.util.stream.Collectors.toList());
        assertEquals(
                "The word should be present on both files",
                expectedPaths,
                actualPaths);
        List<String> expectedFirstPath = Collections.singletonList(textFilePaths.get(0).replace("\\", "/"));
        List<String> actualFirstPath = wordsInFiles.get("voluptate").stream()
                .map(path -> path.replace("\\", "/"))
                .collect(java.util.stream.Collectors.toList());
        assertEquals(
                "The word should be present only on the first file",
                expectedFirstPath,
                actualFirstPath);
        List<String> expectedSecondPath = Collections.singletonList(textFilePaths.get(1).replace("\\", "/"));
        List<String> actualSecondPath = wordsInFiles.get("perspiciatis").stream()
                .map(path -> path.replace("\\", "/"))
                .collect(java.util.stream.Collectors.toList());
        assertEquals(
                "The word should be present only on the second file",
                expectedSecondPath,
                actualSecondPath);
    }
}
