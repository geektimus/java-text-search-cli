package com.codingmaniacs.interviews.cli.search;

import com.codingmaniacs.interviews.cli.search.entities.RankedWord;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordRankerTest {

    final Map<String, List<String>> indexedWords = Stream.of(
            new AbstractMap.SimpleEntry<>(
                    "word1",
                    Arrays.asList(
                            "fake-file-01.txt",
                            "fake-file-02.txt",
                            "fake-file-03.txt",
                            "fake-file-04.txt"
                    )),
            new AbstractMap.SimpleEntry<>(
                    "word2",
                    Arrays.asList(
                            "fake-file-02.txt",
                            "fake-file-04.txt"
                    )),
            new AbstractMap.SimpleEntry<>(
                    "word3",
                    Arrays.asList(
                            "fake-file-01.txt",
                            "fake-file-03.txt"
                    )),
            new AbstractMap.SimpleEntry<>(
                    "word4",
                    Arrays.asList(
                            "fake-file-01.txt",
                            "fake-file-04.txt"
                    )),
            new AbstractMap.SimpleEntry<>(
                    "word5",
                    Collections.singletonList(
                            "fake-file-04.txt"
                    )))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Test
    public void testRankNonIndexedWordZero() {
        WordRanker wordRanker = WordRanker.getInstance();
        wordRanker.initialize(indexedWords);

        Map<String, Integer> rank = wordRanker.getRankForWords(Collections.singletonList("word10"));

        assertEquals("The rank for a word not present in a file is 0 for that specific file", 0, rank.size());
    }

    @Test
    public void testRankIndexedWordOnlyOneHitInOneFile() {
        WordRanker wordRanker = WordRanker.getInstance();
        wordRanker.initialize(indexedWords);

        String filePath = "fake-file-04.txt";

        Map<String, Integer> rankPerFile = wordRanker.getRankForWords(Collections.singletonList("word5"));

        assertEquals("The word should be present in only one file", 1, rankPerFile.size());

        assertTrue("The file path should be the path of the file with the hit", rankPerFile.containsKey(filePath));
        assertEquals("The rank for a word present in a file is 100 for that specific file", 100, rankPerFile.get(filePath).intValue());
    }

    @Test
    public void testRankIndexedWordOnlyMultipleHitsInSeveralFiles() {
        WordRanker wordRanker = WordRanker.getInstance();
        wordRanker.initialize(indexedWords);

        String word = "word1";

        Map<String, Integer> rankPerFile = wordRanker.getRankForWords(Collections.singletonList(word));

        assertEquals("The word should be present in four files", 4, rankPerFile.size());

        String filePath = "fake-file-01.txt";
        assertTrue("The file path should be the path of the file with the hit", rankPerFile.containsKey(filePath));
        assertEquals("The rank for a word present in a file is 100 for that specific file", 100, rankPerFile.get(filePath).intValue());

        filePath = "fake-file-04.txt";
        assertTrue("The file path should be the path of the file with the hit", rankPerFile.containsKey(filePath));
        assertEquals("The rank for a word present in a file is 100 for that specific file", 100, rankPerFile.get(filePath).intValue());
    }

    @Test
    public void testRankIndexedMultipleWordsOneHitInSeveralFiles() {
        WordRanker wordRanker = WordRanker.getInstance();
        wordRanker.initialize(indexedWords);

        List<String> words = Arrays.asList("word10", "word5", "word20");

        Map<String, Integer> rankPerFile = wordRanker.getRankForWords(words);

        assertEquals("The word should be present in one file", 1, rankPerFile.size());

        String filePath = "fake-file-04.txt";
        assertTrue("The file path should be the path of the file with the hit", rankPerFile.containsKey(filePath));
        assertEquals("The rank for a word present in a file is 100 for that specific file", 33, rankPerFile.get(filePath).intValue());;
    }
}
