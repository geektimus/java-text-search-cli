package com.codingmaniacs.interviews.cli.search.entities;

import java.util.Objects;

public class RankedWord implements Comparable<RankedWord> {
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


    @Override
    public int compareTo(RankedWord o) {
        return Integer.compare(o.getRank(), getRank());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankedWord that = (RankedWord) o;
        return getRank() == that.getRank() &&
                getCount() == that.getCount() &&
                getWord().equals(that.getWord()) &&
                getFileName().equals(that.getFileName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord(), getFileName(), getRank(), getCount());
    }
}
