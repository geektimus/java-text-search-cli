package com.codingmaniacs.interviews.cli.search.entities;

public class RankedWord {
    private final String word;
    private final String fileName;
    private final int rank;
    private final int count;


    public RankedWord(String word, String fileName, int rank, int count) {
        this.word = word;
        this.fileName = fileName;
        this.rank = rank;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public String getFileName() {
        return fileName;
    }

    public int getRank() {
        return rank;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "RankedWord{" +
                "word='" + word + '\'' +
                ", fileName='" + fileName + '\'' +
                ", rank=" + rank +
                ", count=" + count +
                '}';
    }
}
