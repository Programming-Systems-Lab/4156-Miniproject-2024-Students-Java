package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for the MyFileDatabase class. */
public class MyFileDatabaseUnitTests {

  private static final String TEST_FILE_PATH = "./test_data.txt";

  private MyFileDatabase myFileDatabase;

  /** Sets up the MyFileDatabase instance before each test. */
  @BeforeEach
  public void setUp() {
    myFileDatabase = new MyFileDatabase(1, TEST_FILE_PATH);
  }

  /** Tests setMapping(). */
  @Test
  public void testSetMapping() {
    Map<String, Department> testMapping = new HashMap<>();
    testMapping.put("FIN", new Department("FIN", new HashMap<>(), "chair1", 120));
    myFileDatabase.setMapping(testMapping);
    assertEquals(testMapping, myFileDatabase.getDepartmentMapping());
  }

  /** Tests getDepartmentMapping(). */
  @Test
  public void testGetDepartmentMapping() {
    assertNull(myFileDatabase.getDepartmentMapping());
  }

  /** Tests toString(). */
  @Test
  public void testToString() {
    Map<String, Department> testMapping = new HashMap<>();
    Department department = new Department("FIN", new HashMap<>(), "chair1", 120);
    testMapping.put("FIN", department);
    myFileDatabase.setMapping(testMapping);
    String expected = "For the FIN department: \n" + department;
    assertEquals(expected, myFileDatabase.toString());
  }

  /** Tests saveContentsToFile(). */
  @Test
  public void testSaveContentsToFile() {
    Map<String, Department> testMapping = new HashMap<>();
    Department department = new Department("FIN", new HashMap<>(), "chair1", 120);
    testMapping.put("FIN", department);
    myFileDatabase.setMapping(testMapping);
    myFileDatabase.saveContentsToFile();
    File file = new File(TEST_FILE_PATH);
    assertTrue(file.exists());
  }

  /** Tests deSerializeObjectFromFile(). */
  @Test
  public void testDeSerializeObjectFromFile() {
    myFileDatabase = new MyFileDatabase(1, "./data.txt");
    Map<String, Department> deserializedMapping = myFileDatabase.deSerializeObjectFromFile();
    assertFalse(deserializedMapping.isEmpty());
  }
}
