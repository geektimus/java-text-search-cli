package com.codingmaniacs.interviews.cli.search.file;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class FileFiltererTest {

    @Test
    public void returnOnlyTextFiles() {
        String directory = "src/test/resources/fake-directory";
        List<String> textFileNames = FileFilterer.getTextFileNames(directory);

        assertThat(textFileNames, hasSize(4));
    }

    @Test
    public void returnEmptyListForNotValidFiles() {
        String directory = "src/test/resources/fake-directory/image-only";
        List<String> textFileNames = FileFilterer.getTextFileNames(directory);

        assertThat(textFileNames, hasSize(0));
    }
}
