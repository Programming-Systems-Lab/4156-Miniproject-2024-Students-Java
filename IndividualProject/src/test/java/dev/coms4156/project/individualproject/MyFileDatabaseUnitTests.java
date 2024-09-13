package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MyFileDatabase class that also includes setup.
 */
public class MyFileDatabaseUnitTests {

  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    myFileDatabase = new MyFileDatabase(0, FILE_PATH);
    testFileDatabase = new MyFileDatabase(0, DUMMY_FILE_PATH);
  }

  @Test
  public void testExistingMappings() {
    Map<String, Department> existingMappings = myFileDatabase.getDepartmentMapping();
    assertNotNull(existingMappings, "Existing mappings should not be null.");
    assertEquals(7, existingMappings.size(), "Mapping size should match the expected number.");
  }

  @Test
  public void testSaveAndLoadContents() {
    Department department = new Department("TEST", new HashMap<>(), "TEST CHAIR", 1500);
    department.createCourse("4118", "Jason Nieh", "501 NWC", "4:10-5:25", 100);

    Map<String, Department> newMapping = new HashMap<>();
    newMapping.put("TEST", department);

    testFileDatabase.setMapping(newMapping);
    testFileDatabase.saveContentsToFile();

    Map<String, Department> testMapping = testFileDatabase.getDepartmentMapping();

    assertNotNull(testMapping, "Loaded mapping should not be null.");
    assertEquals(newMapping.size(), testMapping.size(), "Mapping size should match.");
    assertEquals(department, testMapping.get("TEST"), "Department data should match.");
  }

  @Test
  public void testToString() {
    Department department = new Department("NS", new HashMap<>(), "Nicole Lin", 1500);
    Map<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("Nicole Studies", department);
    testFileDatabase.setMapping(departmentMapping);
    String expectedString = "For the Nicole Studies department: \n" + department.toString();
    assertEquals(
        expectedString, 
        testFileDatabase.toString(), 
        "toString() output should match the expected result."
    );
  }

  public static String FILE_PATH = "./data.txt";
  public static String DUMMY_FILE_PATH = "dummy.txt";
  public static MyFileDatabase myFileDatabase;
  public static MyFileDatabase testFileDatabase;
}