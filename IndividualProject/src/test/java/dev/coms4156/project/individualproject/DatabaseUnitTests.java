package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is just testing if we can read our database from the ./data.txt file
 * and extract departments & courses from the database.
 */
@SpringBootTest
@ContextConfiguration
public class DatabaseUnitTests {
  
  /**
   * We are creating a database with two departments (COMS & ECON) and one courses per department
   * for simplicity sake. 
   */
  @BeforeAll
  public static void setupDatabaseForTesting() {

    testDatabase = new MyFileDatabase(1, testFilePath);
    HashMap<String, Course> courses = new HashMap<>();
    HashMap<String, Department> testMapping = new HashMap<>();

    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    courses.put("1004", coms1004);

    Department compSci = new Department("COMS", courses, "Luca Carloni", 2700);
    testMapping.put("COMS", compSci);

    courses = new HashMap<>();

    Course econ1105 = new Course("Waseem Noor", "309 HAV", "2:40-3:55", 210);
    econ1105.setEnrolledStudentCount(187);
    courses.put("1105", econ1105);

    Department econ = new Department("ECON", courses, "Michael Woodford", 2345);
    testMapping.put("ECON", econ);

    testDatabase.setMapping(testMapping);
    testDatabase.saveContentsToFile();
    
  }
  
  /**
   * Testing the ability to grab the department from the overarching HashMap stored within
   * testMapping and then grabbing a course from that department.
   */
  @Test
  public void getDeptMappingTest() {
    HashMap<String, Department> testMapping = testDatabase.getDepartmentMapping();
    HashMap<String, Course> compSciCourses = testMapping.get("COMS").getCourseSelection();
    assertEquals("Adam Cannon", compSciCourses.get("1004").getInstructorName());
  }

  /**
   * Testing the ability to grab and stringify the 
   * courses from both the COMS and ECON departments in the test database. 
   */
  @Test
  public void toStringTest() {
    String expectedResult = "For the COMS department: \n"
        + "COMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n"
        + "For the ECON department: \n"
        + "ECON 1105: \nInstructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55\n";
    assertEquals(expectedResult, testDatabase.toString());
  }

  /**
   * This test attempts to deserialize the object from the file and check if the object is of the
   * type HashMap with Strings as the key and Department as the value.
   */
  @Test
  public void deSerializeTest() {
    Object obj = testDatabase.deSerializeObjectFromFile();
    assertTrue(obj instanceof HashMap);
  }

  /**
   * This test attempts to get the deSerialized object written to a throwaway file
   * to throw an IllegalArgumentException.
   */
  @Test
  public void deSerializeInvalidTest() {
    try (ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream("./invalidDB.txt"))) {
      out.writeObject("This is a string, not a HashMap!");
    } catch (IOException e) {
      e.printStackTrace();
    }

    MyFileDatabase invalidDb = new MyFileDatabase(1, "./invalidDB.txt");
    assertThrows(IllegalArgumentException.class, () -> {
      invalidDb.deSerializeObjectFromFile();
    });
  }
  
  /**
   * The test department instance used for testing and the courses held within the Department.
   */
  public static MyFileDatabase testDatabase;
  public static String testFilePath = "./testingDB.txt";
}

