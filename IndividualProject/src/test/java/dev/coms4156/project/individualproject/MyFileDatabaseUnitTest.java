package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class represents a unit test for testing MyFileBase implementation.
 */
public class MyFileDatabaseUnitTest {

  private static final String TEST_FILE_PATH = "testDatabase.txt";
  private MyFileDatabase database;
  private Map<String, Department> testMapping;

  /**
   * This method initializes sample data to be used across various test cases:
   * 
   * <p>Instantiates two Course objects, `coms1004` and `coms3134`, with sample 
   * data including instructors, locations, times, and enrolled student counts.
   * Populates a Map with course identifiers and corresponding Course objects.
   * Creates a Department object with a mapping of courses, an instructor name, 
   * and a capacity.
   * Initializes the {@link MyFileDatabase} instance with a file path used for testing.
   */
  @BeforeEach
  public void setup() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    
    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);
    
    testMapping = new HashMap<>();
    testMapping.put("COMS", new Department("COMS", comsCourses, "Phillip Le", 20));
    
    database = new MyFileDatabase(1, TEST_FILE_PATH);
  }

  @Test
  public void setMappingTest() {
    database.setMapping(testMapping);
    Map<String, Department> retrievedMapping = database.getDepartmentMapping();
    assertEquals(testMapping, retrievedMapping);
  }


  @Test
  public void fileDoesNotExistTest() {
    MyFileDatabase newDatabase = new MyFileDatabase(0, "nonexistentFile.txt");
    Map<String, Department> deserializedMapping = newDatabase.getDepartmentMapping();
    assertTrue(deserializedMapping.isEmpty(), 
        "Deserialized mapping should be empty if the file does not exist.");
  }

  /**
   * Cleans up the test environment after each test method.
   */
  @AfterEach
  public void tearDown() {
    File file = new File(TEST_FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }
}
