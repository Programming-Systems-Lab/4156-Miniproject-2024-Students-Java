package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the MyFileDatabase class.
 */
public class MyFileDatabaseTests {

  private MyFileDatabase testDatabase;
  private Department testDepartment;

  /** Sets up database for testing.  */
  @BeforeEach
  public void setUpTestDatabase() {
    Map<String, Department> testDepartmentMapping = new HashMap<>();
    Map<String, Course> courseMapping = new HashMap<>();
    testDepartment = new Department("COMS", courseMapping, "Luca Carloni", 1);
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    courseMapping.put("COMS3251", coms3251);

    testDepartmentMapping.put("COMS", testDepartment);    
    testDatabase = new MyFileDatabase(1, "./data.txt");
    testDatabase.setMapping(testDepartmentMapping);
  }

  @Test
  public void testToString() {
    String expected = "For the COMS department: \n"
                      +
                      "COMS COMS3251: \n"
                      + 
                      "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n";
    assertEquals(expected, testDatabase.toString());
  }
  
}
