package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * MyFileDatabase Unit Tests.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  private static String testFilePath;
  private MyFileDatabase testDatabase;
  private HashMap<String, Department> sampleData;

  /**
   * Set up the file and sample data before running tests.
   */
  @BeforeAll
  public static void setupTestFile() throws IOException {
    // Create a temporary test file
    testFilePath = "testDatabase.txt";
    File testFile = new File(testFilePath);
    testFile.createNewFile();
  }

  /**
   * Initialize sample data and test database before each test.
   */
  @BeforeEach
  public void setup() {
    // Create sample department data
    sampleData = new HashMap<>();
    Department department = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 150);
    sampleData.put("COMS", department);

    testDatabase = new MyFileDatabase(0, testFilePath);
  }

  /**
   * Test for setting and getting the department mapping.
   */
  @Test
  public void testSetAndGetMapping() {
    testDatabase.setMapping(sampleData);
    HashMap<String, Department> retrievedMapping = testDatabase.getDepartmentMapping();
    assertNotNull(retrievedMapping);
    assertEquals(1, retrievedMapping.size());
    assertEquals("Jae Woo Lee", retrievedMapping.get("COMS").getDepartmentChair());
  }

  /**
   * Test deserialization when the file contains valid serialized data.
   */
  @Test
  public void testDeserializeObjectFromFile() throws Exception {
    // Serialize sample data to the test file
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilePath))) {
      out.writeObject(sampleData);
    }

    // Create a new instance of MyFileDatabase and verify deserialization
    MyFileDatabase deserializedDatabase = new MyFileDatabase(0, testFilePath);
    HashMap<String, Department> deserializedData = deserializedDatabase.getDepartmentMapping();
    assertEquals(1, deserializedData.size());
    assertEquals("COMS", deserializedData.keySet().iterator().next());
  }

  /**
   * Test saving contents to a file and verifying the contents are written.
   */
  @Test
  public void testSaveContentsToFile() throws Exception {
    // Set new data to be saved
    testDatabase.setMapping(sampleData);

    // Save data to the test file
    testDatabase.saveContentsToFile();

    // Deserialize and verify the contents of the file
    MyFileDatabase reloadedDatabase = new MyFileDatabase(0, testFilePath);
    HashMap<String, Department> reloadedData = reloadedDatabase.getDepartmentMapping();
    assertEquals(1, reloadedData.size());
    assertEquals("COMS", reloadedData.keySet().iterator().next());
  }

  /**
   * Test string representation of the database.
   */
  @Test
  public void testToString() {
    testDatabase.setMapping(sampleData);
    String expected = "For the COMS department: \n" + sampleData.get("COMS").toString();
    assertEquals(expected, testDatabase.toString());
  }

  /**
   * Cleanup the test file after tests.
   */
  @AfterAll
  public static void cleanupTestFile() {
    File testFile = new File(testFilePath);
    if (testFile.exists()) {
      testFile.delete();
    }
  }
}
