package com.codingmaniacs.interviews.cli.search.text;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StringUtilsTest {
    @Test
    public void testSanitizeEmptyString() {
        String string = "";

        List<String> sanitizedWords = StringUtils.sanitizeAndTokenizeWords(string).collect(Collectors.toList());

        assertThat(sanitizedWords, hasSize(0));
    }

    @Test
    public void testSanitizeOneWord() {
        String string = "sanitized";

        List<String> sanitizedWords = StringUtils.sanitizeAndTokenizeWords(string).collect(Collectors.toList());

        assertThat(sanitizedWords, hasSize(1));
        assertThat(sanitizedWords, hasItem("sanitized"));
    }

    @Test
    public void testSanitizeOneWordMixedCaseAndSymbols() {
        String string = ",saniTIzed.";

        List<String> sanitizedWords = StringUtils.sanitizeAndTokenizeWords(string).collect(Collectors.toList());

        assertThat(sanitizedWords, hasSize(1));
        assertThat(sanitizedWords, hasItem("sanitized"));
    }

    @Test
    public void testSanitizeMultipleWordsMixedCaseAndSymbols() {
        String string = ",saniTIzed. secOND? Main2";

        List<String> sanitizedWords = StringUtils.sanitizeAndTokenizeWords(string).collect(Collectors.toList());

        assertThat(sanitizedWords, hasSize(3));
        assertThat(sanitizedWords, hasItems("sanitized","second", "main2"));
    }
}