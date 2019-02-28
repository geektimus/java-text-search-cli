package com.codingmaniacs.interviews.cli.search.file.entities;

public class IndexedWord {
    private final String word;

    private final String fileName;

    public IndexedWord(String word, String fileName) {
        this.word = word;
        this.fileName = fileName;
    }

    public String getWord() {
        return word;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return String.format("{word: %s, file: %s}", word, fileName);
    }
}
