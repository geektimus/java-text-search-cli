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
        assertEquals("The file name should be the same used for the test", textFilePath, wordsInFile.get("lorem"));

        textFilePath = "src/test/resources/fake-directory/b-text-file.txt";
        wordsInFile = FileIndexer.getIndexedContents(textFilePath);

        assertEquals("The indexer should return the number of words on a file", 70, wordsInFile.size());
        assertEquals("The file name should be the same used for the test", textFilePath, wordsInFile.get("voluptatem"));
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
        assertEquals(
                "The word should be present on both files",
                textFilePaths,
                wordsInFiles.get("dolor"));
        assertEquals(
                "The word should be present only on the first file",
                Collections.singletonList(textFilePaths.get(0)),
                wordsInFiles.get("voluptate"));
        assertEquals(
                "The word should be present only on the second file",
                Collections.singletonList(textFilePaths.get(1)),
                wordsInFiles.get("perspiciatis"));
    }
}
