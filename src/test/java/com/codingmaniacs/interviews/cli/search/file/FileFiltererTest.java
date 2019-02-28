package com.codingmaniacs.interviews.cli.search.file;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileFiltererTest {

    @Test
    public void createFileFilterer() {
        FileFilterer loader = new FileFilterer("non-existent-dir");
        assertNotNull(loader);
    }

    @Test
    public void returnOnlyTextFiles() {
        String directory = "src/test/resources/fake-directory";
        FileFilterer loader = new FileFilterer(directory);
        List<String> textFileNames = loader.getTextFileNames();

        assertEquals("The loader must load only text files", 4, textFileNames.size());
    }

    @Test
    public void returnEmptyListForNotValidFiles() {
        String directory = "src/test/resources/fake-directory/image-only";
        FileFilterer loader = new FileFilterer(directory);
        List<String> textFileNames = loader.getTextFileNames();

        assertEquals("The loader must load only text files", 0, textFileNames.size());
    }
}
