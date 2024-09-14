package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class is built to test the MyFileDatabase class, which we have created for storing
 * the courses and departments in a file.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTest {
  
  @BeforeAll
  public static void setupMyFileDatabaseForTesting() {

    testMyFileDatabase = new MyFileDatabase(0, "data.txt");
  }

  @Test
  public void getMappingTest() {
    assertEquals(7, testMyFileDatabase.getDepartmentMapping().size());
  }

  @Test
  public void setMappingTest() {
    final HashMap<String, Department> currentMapping = testMyFileDatabase.getDepartmentMapping();

    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> testCourses = new HashMap<String, Course>();
    testCourses.put("4156", testCourse);

    Department testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
    HashMap<String, Department> testDepartmentMapping = new HashMap<String, Department>();
    testDepartmentMapping.put("COMS", testDepartment);

    testMyFileDatabase.setMapping(testDepartmentMapping);

    assertEquals(1, testMyFileDatabase.getDepartmentMapping().size());

    testMyFileDatabase.setMapping(currentMapping);

  }

  @Test
  public void toStringTest() {
    final HashMap<String, Department> currentMapping = testMyFileDatabase.getDepartmentMapping();

    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    HashMap<String, Course> testCourses = new HashMap<String, Course>();
    testCourses.put("4156", testCourse);

    Department testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
    HashMap<String, Department> testDepartmentMapping = new HashMap<String, Department>();
    testDepartmentMapping.put("COMS", testDepartment);

    testMyFileDatabase.setMapping(testDepartmentMapping);

    assertEquals("For the COMS department: \nCOMS 4156:"
        + " \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n", 
        testMyFileDatabase.toString());

    testMyFileDatabase.setMapping(currentMapping);
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    assertEquals(7, testMyFileDatabase.deSerializeObjectFromFile().size());
  }

  @Test
  public void saveContentsToFileTest() {
    try {
      MyFileDatabase writingMyFileDatabase = new MyFileDatabase(1, "writing_data.txt");

      Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
      HashMap<String, Course> testCourses = new HashMap<String, Course>();
      testCourses.put("4156", testCourse);

      Department testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
      HashMap<String, Department> testDepartmentMapping = new HashMap<String, Department>();
      testDepartmentMapping.put("COMS", testDepartment);

      writingMyFileDatabase.setMapping(testDepartmentMapping);
      writingMyFileDatabase.saveContentsToFile();

      MyFileDatabase readingMyFileDatabase = new MyFileDatabase(0, "writing_data.txt");
      assertEquals(1, readingMyFileDatabase.getDepartmentMapping().size());

      // clean up (delete the file)
      Path path = Paths.get("writing_data.txt");
      Files.deleteIfExists(path);
    } catch (IOException e) {
      e.printStackTrace(); 
      fail("Failed to delete the file or another IO error occurred.");
    }

  }


  private static MyFileDatabase testMyFileDatabase;
}
