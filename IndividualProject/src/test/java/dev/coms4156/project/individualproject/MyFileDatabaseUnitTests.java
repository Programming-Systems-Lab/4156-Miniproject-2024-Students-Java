package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  private MyFileDatabase testDatabase;
  private HashMap<String, Department> testMapping;

  /**
   * Sets up the test database before each test.
   */
  @BeforeEach
  public void setupDatabaseForTesting() {
    // Create a test database with controlled data
    testMapping = new HashMap<>();

    // Create a mock department with courses
    Department testDepartment = new Department("COMS", new HashMap<>(), "Luca Carloni", 2700);
    testMapping.put("COMS", testDepartment);

    // Initialize the test database with a dummy file path
    testDatabase = new MyFileDatabase(1, "./testData.txt");
    testDatabase.setMapping(testMapping);
  }

  /**
   * Teardown method to wipe the test file after each test, if it exists.
   */
  @AfterEach
  public void tearDown() {
    File file = new File("./testData.txt");
    if (file.exists()) {
      assertTrue(file.delete(), "The test data file should be deleted after each test.");
    }
  }

  /**
   * Test for setMapping method.
   * Ensures that the department mapping is correctly set.
   */
  @Test
  public void testSetMapping() {
    HashMap<String, Department> newMapping = new HashMap<>();
    Department newDepartment = new Department("MATH", new HashMap<>(), "Joan Smith", 500);
    newMapping.put("MATH", newDepartment);

    testDatabase.setMapping(newMapping);
    assertEquals(newMapping, testDatabase.getDepartmentMapping(),
          "The department mapping should be updated.");
  }

  /**
   * Test for deSerializeObjectFromFile method.
   * Ensures that the object is deserialized correctly from a file and checks expected values.
   */
  @Test
  public void testDeSerializeObjectFromFile() throws IOException {
    // Prepare the expected test data before serialization
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));

    Department testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
    HashMap<String, Department> expectedMapping = new HashMap<>();
    expectedMapping.put("COMS", testDepartment);

    // Serialize the expected data to the test file
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./testData.txt"))) {
      out.writeObject(expectedMapping);
    }

    // Deserialization test
    Map<String, Department> deserializedData = testDatabase.deSerializeObjectFromFile();
    assertNotNull(deserializedData, "The deserialized data should not be null.");

    // Check that the deserialized department exists
    assertTrue(deserializedData.containsKey("COMS"),
          "The deserialized data should contain the department 'COMS'.");

    // Retrieve the deserialized department
    Department deserializedDepartment = deserializedData.get("COMS");
    assertNotNull(deserializedDepartment,
          "The deserialized 'COMS' department should not be null.");

    // Check department attributes
    assertEquals("Luca Carloni", deserializedDepartment.getDepartmentChair(),
          "The department chair should be 'Luca Carloni'.");
    assertEquals(2700, deserializedDepartment.getNumberOfMajors(),
          "The number of majors should be 2700.");

    // Check that the course exists in the deserialized department
    assertTrue(deserializedDepartment.getCourseSelection().containsKey("1004"),
          "The deserialized department should contain the course '1004'.");

    // Retrieve the deserialized course
    Course deserializedCourse = deserializedDepartment.getCourseSelection().get("1004");
    assertNotNull(deserializedCourse,
          "The deserialized '1004' course should not be null.");

    // Check course attributes
    assertEquals("Adam Cannon", deserializedCourse.getInstructorName(),
          "The course instructor should be 'Adam Cannon'.");
    assertEquals("417 IAB", deserializedCourse.getCourseLocation(),
          "The course location should be '417 IAB'.");
    assertEquals("11:40-12:55", deserializedCourse.getCourseTimeSlot(),
          "The course time slot should be '11:40-12:55'.");
    assertEquals(400, deserializedCourse.getEnrollmentCapacity(),
          "The course capacity should be 400.");
  }


  /**
   * Test for saveContentsToFile method.
   * Ensures that the department mapping is correctly saved to a file.
   */
  @Test
  public void testSaveContentsToFile() throws IOException {
    // Call the save method to write to the test file
    testDatabase.saveContentsToFile();

    // Ensure the file was created and has contents
    File file = new File("./testData.txt");
    assertTrue(file.exists(), "The file should exist after saving.");
    assertTrue(file.length() > 0, "The file should not be empty after saving.");
  }

  /**
   * Test for getDepartmentMapping method.
   * Ensures that the department mapping can be retrieved.
   */
  @Test
  public void testGetDepartmentMapping() {
    assertNotNull(testDatabase.getDepartmentMapping(),
          "Department mapping should not be null.");
    assertEquals(testMapping, testDatabase.getDepartmentMapping(),
          "Department mapping should match the test data.");
  }

  /**
   * Test for toString method.
   * Ensures that the string representation of the database is correct.
   */
  @Test
  public void testToString() {
    String dbString = testDatabase.toString();
    assertNotNull(dbString,
          "String representation should not be null.");
    assertTrue(dbString.contains("COMS"),
          "String representation should contain the department name 'COMS'.");
  }
}
