package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the {@link MyFileDatabase} class. It tests the
 * functionality related to file-based storage, including serialization, deserialization,
 * setting/getting department mappings, and generating a string representation of the data.
 *
 * <p>Each test method is designed to verify a specific behavior of the {@code MyFileDatabase}
 * class. A fresh test database is set up before each test and cleaned up after the test is run.
 *
 * <p>Tested functionalities include saving and loading data to/from a file, verifying
 * department mappings, and generating a valid string output.
 */
public class MyFileDatabaseTests {

  private static final String TEST_FILE_PATH = "./test_data.txt";
  private MyFileDatabase myFileDatabase;

  /**
   * Sets up a fresh MyFileDatabase instance and sample data before each test.
   */
  @BeforeEach
  public void setUp() {
    // Set up a fresh database for each test
    myFileDatabase = new MyFileDatabase(1, TEST_FILE_PATH);
    myFileDatabase.setMapping(generateSampleData());
  }

  /**
   * Cleans up by deleting the test data file after each test.
   */
  @AfterEach
  public void tearDown() {
    // Delete the test file after each test
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }

  /**
   * Test to check if the data is serialized and saved successfully to the file.
   */
  @Test
  public void testSaveContentsToFile() {
    myFileDatabase.saveContentsToFile();

    // Check if the file is created
    File file = new File(TEST_FILE_PATH);
    assertTrue(file.exists(), "Test file should be created after saving.");

    // Reload the database from file and check if data matches
    MyFileDatabase loadedDatabase = new MyFileDatabase(0, TEST_FILE_PATH);
    assertNotNull(loadedDatabase.getDepartmentMapping(), "Loaded data should not be null.");
    assertEquals(1, loadedDatabase.getDepartmentMapping().size(),
        "Loaded data should contain one department.");
  }

  /**
   * Test to check deserialization of the data from the file.
   */
  @Test
  public void testDeserializeObjectFromFile() {
    myFileDatabase.saveContentsToFile();

    // Create a new instance and load data from the file
    MyFileDatabase loadedDatabase = new MyFileDatabase(0, TEST_FILE_PATH);
    HashMap<String, Department> departmentMapping = loadedDatabase.getDepartmentMapping();

    assertNotNull(departmentMapping, "Department mapping should not be null.");
    assertEquals(1, departmentMapping.size(), "Department mapping should contain one department.");
    assertTrue(departmentMapping.containsKey("COMS"), "COMS department should be present.");
    assertEquals("John Doe", departmentMapping.get("COMS").getDepartmentChair(),
        "Department chair should be John Doe.");
  }

  /**
   * Test for setting and getting the department mapping.
   */
  @Test
  public void testSetAndGetMapping() {
    HashMap<String, Department> newMapping = new HashMap<>();
    myFileDatabase.setMapping(newMapping);
    assertEquals(newMapping, myFileDatabase.getDepartmentMapping(),
        "The set mapping should match the returned mapping.");
  }

  /**
   * Test the toString method for the database.
   */
  @Test
  public void testToString() {
    String result = myFileDatabase.toString();
    System.out.println(result);
    assertTrue(result.contains("COMS"), "The string representation should contain 'COMS'.");
    assertTrue(result.contains("Adam Cannon"),
        "The string representation should contain 'Adam Cannon'.");
  }

  /**
   * Helper method to generate sample data for testing.
   *
   * @return a HashMap containing a sample department with courses
   */
  private HashMap<String, Department> generateSampleData() {
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1001", new Course("Adam Cannon", "MUDD", "10:00-11:15", 300));
    Department compSci = new Department("COMS", courses, "John Doe", 500);
    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("COMS", compSci);
    return departmentMapping;
  }
}
