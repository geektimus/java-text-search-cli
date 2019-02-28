package com.codingmaniacs.interviews.cli.search;

import com.codingmaniacs.interviews.cli.search.entities.RankedWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordIndex {

    private static WordIndex instance;

    private static Map<String, List<String>> indexedWords;

    private WordIndex() {
    }

    public static WordIndex getInstance() {
        if (instance == null) {
            instance = new WordIndex();
        }
        return instance;
    }

    /**
     * Initializes the WordIndex with a map that contains the mapping from words to file name.
     *
     * @param newIndex Map that contains the mapping from words to file name.
     */
    public void initialize(Map<String, List<String>> newIndex) {
        if (indexedWords == null || indexedWords.isEmpty()) {
            indexedWords = newIndex;
        }
    }

    /**
     * Find the files that contain a given word.
     *
     * @param word Word to be searched in the index.
     * @return List of file names.
     */
    private List<String> getFilesThatMatchWord(String word) {
        if (!indexedWords.containsKey(word))
            return Collections.emptyList();

        return indexedWords
                .get(word);
    }

    /**
     * Calculates the rank of each word per file name.
     * The rank is calculated as number of hits on a file * 100 / number of words to be searched
     * <p>
     * i.e:
     * "search "aaa", "bbb", "ccc"
     * "aaa" appears in file a and file b
     * the rank is 1 * 100 / 3 = 33% for file a and file b
     * </p>
     *
     * @param words Words that the user wants to match against the index
     * @return a List of RankedWords sorted by Rank
     * @see RankedWord
     */
    public List<RankedWord> getRankForWords(List<String> words) {

        Map<String, List<RankedWord>> collect = words
                .stream()
                .flatMap(w -> getFilesThatMatchWord(w).stream().map(fn -> new RankedWord(w, fn, 0, 1)))
                .collect(Collectors.groupingBy(RankedWord::getFileName));

        // TODO: Find how to collect doing this operation, using groupingBy and mapping
        List<RankedWord> ranksPerFile = new ArrayList<>(collect.size());
        for (Map.Entry<String, List<RankedWord>> entry : collect.entrySet()) {
            RankedWord rw = entry.getValue().get(0);
            ranksPerFile.add(
                    new RankedWord(
                            rw.getWord(),
                            rw.getFileName(),
                            entry.getValue().size() * 100 / words.size(),
                            1)
            );
        }

        return ranksPerFile.stream().sorted().collect(Collectors.toList());
    }
}
