package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class IndividualProjectApplicationTest {

    @TempDir
    File tempDir;

    private IndividualProjectApplication app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        app = new IndividualProjectApplication();
        System.setOut(new PrintStream(outContent));
        testFilePath = new File(tempDir, "testData.txt").getAbsolutePath();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        new File(testFilePath).delete();
    }

    @Test
    void testRunSetupArgument() {
        app.run("setup");
        assertTrue(outContent.toString().contains("System Setup"));
        assertNotNull(IndividualProjectApplication.myFileDatabase);
    }

    @Test
    void testRunNoSetupArgument() {
        app.run();
        assertTrue(outContent.toString().contains("Start up"));
        assertNotNull(IndividualProjectApplication.myFileDatabase);
    }

}