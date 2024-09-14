package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MyFileDatabase class.
 */
public class MyFileDatabaseUnitTests {

  private static final String TEST_FILE_PATH = "./testData.txt";
  private MyFileDatabase fileDatabase;

  @BeforeEach
  public void setUp() {
    fileDatabase = new MyFileDatabase(1, TEST_FILE_PATH);  // Test constructor for saving mode
  }

  @AfterEach
  public void tearDown() {
    // Clean up the test file after each test
    File testFile = new File(TEST_FILE_PATH);
    if (testFile.exists()) {
      testFile.delete();
    }
  }

  @Test
  public void testSaveAndDeserialize() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    Course coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);

    HashMap<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);

    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", comsDept);

    fileDatabase.setMapping(departmentMapping);
    fileDatabase.saveContentsToFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, TEST_FILE_PATH);  // Test loading mode
    HashMap<String, Department> loadedMapping = loadedDatabase.getDepartmentMapping();

    assertNotNull(loadedMapping, "Loaded department mapping should not be null.");
    assertTrue(loadedMapping.containsKey("COMS"), "COMS department should exist after deserialization.");
    assertEquals("Luca Carloni", loadedMapping.get("COMS").getDepartmentChair(), "Department chair should match.");
  }

  @Test
  public void testInvalidFilePath() {
    MyFileDatabase invalidDatabase = new MyFileDatabase(0, "./nonExistentFile.txt");
    assertNull(invalidDatabase.getDepartmentMapping(), "Loading from non-existent file should return null.");
  }

  @Test
  public void testCorruptedFileHandling() throws IOException {
    File corruptedFile = new File(TEST_FILE_PATH);
    try (FileOutputStream fos = new FileOutputStream(corruptedFile)) {
      fos.write("InvalidData".getBytes());
    }

    MyFileDatabase corruptedDatabase = new MyFileDatabase(0, TEST_FILE_PATH);

    assertNull(corruptedDatabase.getDepartmentMapping(), "Corrupted file should result in a null department mapping.");
  }

  @Test
  public void testSetMapping() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    HashMap<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);

    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    HashMap<String, Department> initialMapping = new HashMap<>();
    initialMapping.put("COMS", comsDept);

    fileDatabase.setMapping(initialMapping);
    HashMap<String, Department> mappingFromDatabase = fileDatabase.getDepartmentMapping();

    assertEquals(initialMapping, mappingFromDatabase, "Mapping should match the set mapping.");
  }

  @Test
  public void testToString() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    Course coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);

    HashMap<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);

    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", comsDept);

    fileDatabase.setMapping(departmentMapping);

    String result = fileDatabase.toString();

    String expectedOutput = "For the COMS department: \n" +
        "COMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n" +
        "COMS 3134: \nInstructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n";

    assertEquals(expectedOutput, result, "toString output should match the expected format.");
  }

  @Test
  public void testEmptyDatabaseHandling() {
    HashMap<String, Department> emptyMapping = new HashMap<>();
    fileDatabase.setMapping(emptyMapping);

    assertTrue(fileDatabase.getDepartmentMapping().isEmpty(), "Department mapping should be empty.");
  }

  @Test
  public void testInvalidDataInFile() throws IOException {
    File invalidFile = new File(TEST_FILE_PATH);
    try (FileOutputStream fos = new FileOutputStream(invalidFile)) {
      fos.write("SomeInvalidData".getBytes());
    }

    MyFileDatabase invalidDatabase = new MyFileDatabase(0, TEST_FILE_PATH);

    assertNull(invalidDatabase.getDepartmentMapping(), "Invalid data should result in a null department mapping.");
  }
}
