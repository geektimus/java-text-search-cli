package com.codingmaniacs.interviews.cli.search;

import com.codingmaniacs.interviews.cli.search.entities.RankedWord;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;

public class WordIndexTest {

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
        WordIndex wordIndex = WordIndex.getInstance();
        wordIndex.initialize(indexedWords);

        List<RankedWord> rank = wordIndex.getRankForWords(Collections.singletonList("word10"));

        assertEquals("The rank for a word not present in a file is 0 for that specific file", 0, rank.size());
    }

    @Test
    public void testRankIndexedWordOnlyOneHitInOneFile() {
        WordIndex wordIndex = WordIndex.getInstance();
        wordIndex.initialize(indexedWords);

        String filePath = "fake-file-04.txt";

        List<RankedWord> rankPerFile = wordIndex.getRankForWords(Collections.singletonList("word5"));

        assertThat(rankPerFile, hasSize(1));

        assertThat(rankPerFile, hasItem(new RankedWord("word5", filePath, 100, 1)));
    }

    @Test
    public void testRankIndexedWordOnlyMultipleHitsInSeveralFiles() {
        WordIndex wordIndex = WordIndex.getInstance();
        wordIndex.initialize(indexedWords);

        String word = "word1";

        List<RankedWord> rankPerFile = wordIndex.getRankForWords(Collections.singletonList(word));

        assertThat(rankPerFile, hasSize(4));

        String filePath = "fake-file-02.txt";
        assertThat(rankPerFile, hasItem(new RankedWord("word1", filePath, 100, 1)));

        filePath = "fake-file-04.txt";
        assertThat(rankPerFile, hasItem(new RankedWord("word1", filePath, 100, 1)));
    }

    @Test
    public void testRankIndexedMultipleWordsOneHitInOneFile() {
        WordIndex wordIndex = WordIndex.getInstance();
        wordIndex.initialize(indexedWords);

        List<String> words = Arrays.asList("word10", "word5", "word20");

        List<RankedWord> rankPerFile = wordIndex.getRankForWords(words);

        assertThat(rankPerFile, hasSize(1));

        String filePath = "fake-file-04.txt";
        assertThat(rankPerFile, hasItem(new RankedWord("word5", filePath, 33, 1)));
    }

    @Test
    public void testRankIndexedMultipleWordsSeveralHitsInSeveralFiles() {
        WordIndex wordIndex = WordIndex.getInstance();
        wordIndex.initialize(indexedWords);

        RankedWord[] expectedRanking = {
                new RankedWord("word2", "fake-file-04.txt", 100, 1),
                new RankedWord("word2", "fake-file-02.txt", 66, 1),
                new RankedWord("word4", "fake-file-01.txt", 66, 1),
                new RankedWord("word1", "fake-file-03.txt", 33, 1)
        };

        List<String> words = Arrays.asList("word2", "word4", "word1");

        List<RankedWord> rankPerFile = wordIndex.getRankForWords(words);

        assertThat(rankPerFile, hasSize(4));
        assertThat(rankPerFile, hasItems(expectedRanking));
    }
}
