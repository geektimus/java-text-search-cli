package com.codingmaniacs.interviews.cli.search;

import com.codingmaniacs.interviews.cli.search.entities.RankedWord;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// FIXME: Maybe call this wordindex???
public class WordRanker {

    private static WordRanker instance;

    private static Map<String, List<String>> indexedWords;

    private WordRanker() {
    }

    public static WordRanker getInstance() {
        if (instance == null) {
            instance = new WordRanker();
        }
        return instance;
    }

    public void initialize(Map<String, List<String>> newIndex) {
        if (indexedWords == null || indexedWords.isEmpty()) {
            indexedWords = newIndex;
        }
    }

    private List<String> getFilesThatMatchWord(String word) {
        if (!indexedWords.containsKey(word))
            return Collections.emptyList();

        return indexedWords
                .get(word);
    }

    public Map<String, Integer> getRankForWords(List<String> words) {

        Map<String, List<RankedWord>> collect = words
                .stream()
                .flatMap(w -> getFilesThatMatchWord(w).stream().map(fn -> new RankedWord(w, fn, 0, 1)))
                .collect(Collectors.groupingBy(RankedWord::getFileName));

        // TODO: Find how to collect doing this operation, using groupingBy and mapping
        Map<String, Integer> ranksPerFile = new HashMap<>(collect.size());
        for (Map.Entry<String, List<RankedWord>> entry : collect.entrySet()) {
            ranksPerFile.put(entry.getKey(), entry.getValue().size() * 100 / words.size() );
        }

        return ranksPerFile;
    }
}
