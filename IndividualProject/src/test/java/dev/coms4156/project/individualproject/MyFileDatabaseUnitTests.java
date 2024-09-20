package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MyFileDatabase class.
 */

public class MyFileDatabaseUnitTests {

  private final String testFilePath = "testDatabase.ser";
  private MyFileDatabase myFileDatabase;

  /**
   * Sets up the test environment before each test runs.
   */

  @BeforeEach
  public void setup() {
    File file = new File(testFilePath);
    if (file.exists()) {
      file.delete();
    }

    myFileDatabase = new MyFileDatabase(1, testFilePath);
  }

  @Test
  public void testSetAndGetMapping() {
    HashMap<String, Department> departments = new HashMap<>();
    departments.put("ELEN", new Department("ELEN", new HashMap<>(), "this.departmentChair", 250));
    myFileDatabase.setMapping(departments);

    assertEquals(departments, myFileDatabase.getDepartmentMapping());
  }

  @Test
  public void testSaveAndLoadContentsToFile() {
    HashMap<String, Department> departments = new HashMap<>();
    departments.put("ELEN", new Department("ELEN", new HashMap<>(), "this.departmentChair", 250));
    myFileDatabase.setMapping(departments);

    myFileDatabase.saveContentsToFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);
    HashMap<String, Department> loadedMapping = loadedDatabase.getDepartmentMapping();

    assertNotNull(loadedMapping);
    assertEquals(departments.size(), loadedMapping.size());
    assertEquals(departments.get("ELEN").toString(), loadedMapping.get("ELEN").toString());
  }

  @Test
  public void testSaveWithEmptyMapping() {
    myFileDatabase.setMapping(new HashMap<>());

    myFileDatabase.saveContentsToFile();
    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);

    assertNotNull(loadedDatabase.getDepartmentMapping());
    assertTrue(loadedDatabase.getDepartmentMapping().isEmpty());
  }

  @Test
  public void testDeserializeInvalidFile() throws IOException {
    File file = new File(testFilePath);
    file.createNewFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);
    assertNull(loadedDatabase.getDepartmentMapping());
  }

  @Test
  public void testDeserializeFromCorruptedFile() throws IOException {
    File file = new File(testFilePath);
    file.createNewFile();

    MyFileDatabase corruptedDatabase = new MyFileDatabase(0, testFilePath);
    assertNull(corruptedDatabase.getDepartmentMapping());
  }

  @Test
  public void testToString() {
    HashMap<String, Department> departments = new HashMap<>();
    Department department = new Department("ELEN", new HashMap<>(), "this.departmentChair", 250);
    departments.put("ELEN", department);
    myFileDatabase.setMapping(departments);

    String expectedOutput = "For the ELEN department: \n" + department.toString();
    assertTrue(myFileDatabase.toString().contains(expectedOutput));
  }

  @Test
  public void testToStringWithEmptyMapping() {
    myFileDatabase.setMapping(new HashMap<>());

    assertEquals("{}", myFileDatabase.toString());
  }

  @Test
  public void testUpdateAndSaveMapping() {
    HashMap<String, Department> departments = new HashMap<>();
    departments.put("ELEN", new Department("ELEN", new HashMap<>(), "this.departmentChair", 250));
    myFileDatabase.setMapping(departments);

    departments.put("CHEM", new Department("CHEM", new HashMap<>(), "this.departmentChair", 250));
    myFileDatabase.setMapping(departments);
    myFileDatabase.saveContentsToFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);
    HashMap<String, Department> loadedMapping = loadedDatabase.getDepartmentMapping();

    assertEquals(2, loadedMapping.size());
    assertEquals("this.departmentChair", loadedMapping.get("CHEM").getDepartmentChair());
  }

  @Test
  public void testSaveAndDeserializeNullMapping() throws IOException {
    myFileDatabase.setMapping(null);
    myFileDatabase.saveContentsToFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);
    assertNull(loadedDatabase.getDepartmentMapping());
  }

  @Test
  public void testDeserializeWithInvalidObjectType() throws IOException {
    File file = new File(testFilePath);
    file.createNewFile();

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, testFilePath);
    assertNull(loadedDatabase.getDepartmentMapping());
  }

  @Test
  public void testDeserializeFromNonExistentFile() {
    File file = new File("nonExistentFile.ser");
    if (file.exists()) {
      file.delete();
    }

    MyFileDatabase loadedDatabase = new MyFileDatabase(0, "nonExistentFile.ser");
    assertNull(loadedDatabase.getDepartmentMapping());
  }

  @Test
  public void testToStringWithMultipleDepartments() {
    HashMap<String, Department> departments = new HashMap<>();
    Department department1 = new Department("ELEN", new HashMap<>(), "this.departmentChair", 250);
    Department department2 = new Department("CHEM", new HashMap<>(), "this.departmentChair", 250);
    departments.put("ELEN", department1);
    departments.put("CHEM", department2);
    myFileDatabase.setMapping(departments);

    String result = myFileDatabase.toString();
    assertTrue(result.contains("For the ELEN department"));
    assertTrue(result.contains("For the CHEM department"));
  }
}
