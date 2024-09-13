package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * def of MyfileDatabaseUnitTests.
*/
@SpringBootTest
@ContextConfiguration
public class MyfileDatabaseUnitTests {

  @Test
    public void testToString() {
        
    // Create test data
    String[] times = { "11:40-12:55" };
    String[] locations = { "417 IAB" };

    Course course1 = new Course("Andrew", locations[0], times[0], 400);

    HashMap<String, Course> courses1 = new HashMap<>();
    courses1.put("1001", course1);
        

    Department department1 = new Department("CS", courses1, "Andrew", 500);

    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", department1);

    // Create the MyFileDatabase instance and set the mapping
    MyFileDatabase db = new MyFileDatabase(1, "dummyPath"); 
    // The filePath is not used in this test
    db.setMapping(departmentMapping);

    // Expected string representation
    String expectedString = 
            "For the CS department: \n"
            + "CS 1001: \n"
            + "Instructor: Andrew; Location: 417 IAB; Time: 11:40-12:55\n";

    System.err.println(db.toString());
    // Assert that the toString() output matches the expected result
    assertEquals(expectedString, db.toString());
  }

  @Test
    public void testSaveContentsToFile() {

    // Create test data
    String[] times = { "11:40-12:55" };
    String[] locations = { "417 IAB" };

    Course course1 = new Course("Andrew", locations[0], times[0], 400);

    HashMap<String, Course> courses1 = new HashMap<>();
    courses1.put("1001", course1);
        

    Department department1 = new Department("CS", courses1, "Andrew", 500);

    HashMap<String, Department> departmentMapping = new HashMap<>();
    departmentMapping.put("CS", department1);

    // Create the MyFileDatabase instance and set the mapping
    MyFileDatabase db = new MyFileDatabase(1, "dummyPath"); // The filePath is not used in this test

    db.saveContentsToFile();
  }

  @Test
  public void testDeSerializeObjectFromFile() {
    // Create the MyFileDatabase instance and set the mapping
    MyFileDatabase db = new MyFileDatabase(1, "dummyPath"); // The filePath is not used in this test

    // db.deSerializeObjectFromFile();
    // Assert that the method throws IllegalArgumentException
    assertThrows(IllegalArgumentException.class, () -> {
      db.deSerializeObjectFromFile();
    });
  }

}
