package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains all the unit tests for the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration

public class MyFileDatabaseUnitTests {
  private MyFileDatabase database;
  private HashMap<String, Department> testMapping;

  /**
   * Set up the objects for testing.
   */
  @BeforeEach
  public void setup() throws IOException {
    // Initialize a test mapping
    testMapping = new HashMap<>();
    Department testDepartment = new Department("CS", new HashMap<>(), "Dr. Smith", 50);
    testMapping.put("CS", testDepartment);

    // Initialize the database with flag 0 to trigger deserialization
    database = new MyFileDatabase(0, "./data.txt");
    database.setMapping(testMapping);
    database.saveContentsToFile();
  }

  @Test
  public void constructorTest() {
    HashMap<String, Department> mapping = database.getDepartmentMapping();
    assertNotNull(mapping);
    assertTrue(mapping.containsKey("CS"));
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    HashMap<String, Department> mapping = database.deSerializeObjectFromFile();
    assertNotNull(mapping);
    assertTrue(mapping.containsKey("CS"));
  }

  @Test
  public void saveContentsToFileTest() {
    database.setMapping(testMapping); // Set and save contents
    database.saveContentsToFile();
    // Re-create the database to verify saved contents
    MyFileDatabase newDatabase = new MyFileDatabase(0, "./data.txt");
    HashMap<String, Department> mapping = newDatabase.getDepartmentMapping();
    assertNotNull(mapping);
    assertTrue(mapping.containsKey("CS"));
  }

  @Test
  public void setMappingTest() {
    HashMap<String, Department> newMapping = new HashMap<>();
    Department newDepartment = new Department("Math", new HashMap<>(), "Dr. Jones", 30);
    newMapping.put("Math", newDepartment);
    database.setMapping(newMapping);
    assertEquals(newMapping, database.getDepartmentMapping());
  }

  @Test
  public void getDepartmentMappingTest() {
    assertEquals(testMapping, database.getDepartmentMapping());
  }

  @Test
  public void toStringTest() {
    String expectedString = "For the CS department: \n"; // no course case
    assertEquals(expectedString, database.toString());
  }

}
