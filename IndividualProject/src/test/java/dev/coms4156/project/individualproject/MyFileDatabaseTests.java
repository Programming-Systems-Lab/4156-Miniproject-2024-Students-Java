package dev.coms4156.project.individualproject;

import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains unit tests for the MyFileDatabase class.
 */
public class MyFileDatabaseTests {

  private MyFileDatabase testDatabase;
  private Map<String, Department> testDepartmentMapping;
  private Department testDepartment;

  /** Sets up database for testing  */
  @BeforeEach
  public void setUpTestDatabase() {
    testDepartmentMapping = new HashMap<>();
    Map<String, Course> courseMapping = new HashMap<>(); 
    
    testDatabase = new MyFileDatabase(1, "./data.txt");
    testDepartmentMapping = testDatabase.deSerializeObjectFromFile();
    testDatabase.setMapping(testDepartmentMapping);
  }

  @Test
  public void testToString() {
    String expected = "For the COMS department: \n";

    assertEquals(expected, testDatabase.toString());
  }

  
}
