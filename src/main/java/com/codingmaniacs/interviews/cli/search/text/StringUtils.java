package com.codingmaniacs.interviews.cli.search.text;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringUtils {

    private static final String CHARS_TO_BE_IGNORED = "[.,?!]";
    private static final String WORD_SEPARATOR = " ";

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
