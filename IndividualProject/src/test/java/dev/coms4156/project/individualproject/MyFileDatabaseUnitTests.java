package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
 * This test class verifies the functionality of the {@link MyFileDatabase} class 
 * by testing its methods.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  /** 
   * This @BeforeEach function sets up the testFilepath for unit
   * testing in the remainder of the @Test functions.
   */
  @BeforeEach
  public void setupTest() {
    testFilepath = "./testData.txt";
  
  }

  /** 
   * This @AfterEach function makes sure to delete the generated
   * file if it exists after each @Test functions.
   */
  @AfterEach
  public void deleteTestFile() {
    File testFile = new File(testFilepath);
    if (testFile.exists()) {
      testFile.delete();
    }
  }

  @Test
  public void constructorWithNonZeroFlagTest() {
    testDB = new MyFileDatabase(1, "./data.txt");
    assertNull(testDB.getDepartmentMapping());
  }

  @Test
  public void setMappingTest() {
    testDB = new MyFileDatabase(1, "./data.txt");
    HashMap<String, Department> mapping = new HashMap<>();
    HashMap<String, Course> courseMapping = new HashMap<>();
    Department testDept = new Department("COMS", courseMapping, "Luca Carloni", 2700);
    mapping.put("COMS", testDept);
    testDB.setMapping(mapping);
    assertEquals(mapping, testDB.getDepartmentMapping());
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    testDB = new MyFileDatabase(1, "./data.txt");
    testDB.setNewMapping();
    assertNotNull(testDB.getDepartmentMapping());
  }

  @Test
  public void deserializeObjectFromNonexistentFileTest() {
    testDB = new MyFileDatabase(1, "./data.txt");
    testDB.setNewFilepath("./randomPath.txt");
    testDB.setNewMapping();
    assertNull(testDB.getDepartmentMapping());
  }

  @Test
  public void deserializeInvalidObjectType() throws IOException {
    testDB = new MyFileDatabase(1, testFilepath);
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(testFilepath))) {
      out.writeObject("Random invalid object :)");
    }
    assertThrows(IllegalArgumentException.class, () -> testDB.setNewMapping());
    
  }

  @Test
  public void saveContentsToFile() {
    testDB = new MyFileDatabase(1, "./data.txt");
    testDB.setNewFilepath(testFilepath);
    HashMap<String, Department> mapping = new HashMap<>();
    Department testDept = new Department("COMS", new HashMap<>(), "Luca Carloni", 2700);
    mapping.put("COMS", testDept);
    
    testDB.setMapping(mapping);
    testDB.saveContentsToFile();

    HashMap<String, Department> loadedMapping = testDB.getDepartmentMapping();
    
    assertNotNull(loadedMapping);
    assertEquals(1, loadedMapping.size());
    assertTrue(loadedMapping.containsKey("COMS"));
    assertEquals("Luca Carloni", loadedMapping.get("COMS").getDepartmentChair());
  }

  @Test
  public void toStringTest() {
    testDB = new MyFileDatabase(1, "./data.txt");
    HashMap<String, Department> mapping = new HashMap<>();
    Department testDept = new Department("COMS", new HashMap<>(), "Luca Carloni", 2700);
    mapping.put("COMS", testDept);

    testDB.setMapping(mapping);
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Department> entry : mapping.entrySet()) {
      String key = entry.getKey();
      Department value = entry.getValue();
      result.append("For the ").append(key).append(" department: \n").append(value.toString());
    }
    assertEquals(result.toString(), testDB.toString());
  }



  /** The test Database instance used for testing. */
  public static MyFileDatabase testDB;
  public static HashMap<String, Department> testMapping;
  public static String testFilepath;
}



  

