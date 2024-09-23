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
    testMapping = new HashMap<>();

    Department testDepartment = new Department("COMS", new HashMap<>(), "Luca Carloni", 2700);
    testMapping.put("COMS", testDepartment);

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
      assertTrue(file.delete());
    }
  }

  /**
   * Test for setMapping method.
   * Checks that the department mapping is correctly set.
   */
  @Test
  public void setMappingTest() {
    HashMap<String, Department> newMapping = new HashMap<>();
    Department newDepartment = new Department("MATH", new HashMap<>(),
          "Joan Smith", 500);
    newMapping.put("MATH", newDepartment);

    testDatabase.setMapping(newMapping);
    assertEquals(newMapping, testDatabase.getDepartmentMapping());
  }

  /**
   * Test for deSerializeObjectFromFile method.
   * This test checks inserts data into the Database
   * It then checks that the data is deserialized from file correctly + checks expected values.
   */
  @Test
  public void deSerializeObjectFromFileTest() throws IOException {
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));

    Department testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
    HashMap<String, Department> expectedMapping = new HashMap<>();
    expectedMapping.put("COMS", testDepartment);

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./testData.txt"))) {
      out.writeObject(expectedMapping);
    }

    Map<String, Department> deserializedData = testDatabase.deSerializeObjectFromFile();

    assertTrue(deserializedData.containsKey("COMS"));

    Department deserializedDepartment = deserializedData.get("COMS");

    assertEquals("Luca Carloni", deserializedDepartment.getDepartmentChair());
    assertEquals(2700, deserializedDepartment.getNumberOfMajors());

    assertTrue(deserializedDepartment.getCourseSelection().containsKey("1004"));

    Course deserializedCourse = deserializedDepartment.getCourseSelection().get("1004");
    assertNotNull(deserializedCourse);

    // Check course attributes
    assertEquals("Adam Cannon", deserializedCourse.getInstructorName());
    assertEquals("417 IAB", deserializedCourse.getCourseLocation());
    assertEquals("11:40-12:55", deserializedCourse.getCourseTimeSlot());
    assertEquals(400, deserializedCourse.getEnrollmentCapacity());
  }


  /**
   * Test for saveContentsToFile method.
   */
  @Test
  public void saveContentsToFileTest() throws IOException {
    // Write to test file
    testDatabase.saveContentsToFile();

    // Ensure the file was created and isn't empty
    File file = new File("./testData.txt");
    assertTrue(file.exists());
    assertTrue(file.length() > 0);
  }

  /**
   * Test for getDepartmentMapping method.
   */
  @Test
  public void getDepartmentMappingTest() {
    assertEquals(testMapping, testDatabase.getDepartmentMapping());
  }

  /**
   * Test for toString method.
   */
  @Test
  public void toStringTest() {
    String dbString = testDatabase.toString();
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : testDatabase.getDepartmentMapping().entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    assertEquals(result.toString(),dbString);
  }
}
