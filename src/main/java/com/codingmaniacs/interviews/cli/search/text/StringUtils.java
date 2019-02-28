package com.codingmaniacs.interviews.cli.search.text;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringUtils {

    private static final String CHARS_TO_BE_IGNORED = "[.,?!]";
    private static final String WORD_SEPARATOR = " ";

    /**
     * Tokenizes the words contained on a text line, removing unwanted characters.
     *
     * @param textLine Line of text to be sanitized
     * @return Stream containing the words as tokens
     */
    public static Stream<String> sanitizeAndTokenizeWords(String textLine) {
        if (textLine == null || textLine.trim().isEmpty()) {
            return Stream.empty();
        }

        return Arrays
                .stream(
                        textLine
                                .toLowerCase()
                                .replaceAll(CHARS_TO_BE_IGNORED, "")
                                .split(WORD_SEPARATOR)
                );
    }
}
