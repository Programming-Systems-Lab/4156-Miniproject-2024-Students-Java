package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class is built to test the IndividualProjectApplication class, 
 * which we have created to represent the application that manages the 
 * courses and departments.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTest {

  /**
   * This method sets up the course for testing.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    // Firstly we set up the dataset for the test
    application = new IndividualProjectApplication();
    String[] args = {"setup"};
    application.run(args);
  }

  @Test
  public void runTest() {
    MyFileDatabase myFileDatabase = IndividualProjectApplication.myFileDatabase;
    assertEquals(7, myFileDatabase.getDepartmentMapping().size());
  }

  @Test
  public void overrideDatabaseTest() {
    final MyFileDatabase testMyFileDatabase = new MyFileDatabase(0, "data.txt");

    Course testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Map<String, Course> testCourses = new HashMap<>();
    testCourses.put("4156", testCourse);

    Department testDepartment = new Department("COMS", testCourses, "Griffin Newbold", 2700);
    Map<String, Department> testDepartmentMapping = new HashMap<>();
    testDepartmentMapping.put("COMS", testDepartment);

    MyFileDatabase overideMyFileDatabase = new MyFileDatabase(1, "override_data.txt");
    overideMyFileDatabase.setMapping(testDepartmentMapping);
    IndividualProjectApplication.overrideDatabase(overideMyFileDatabase);

    MyFileDatabase myFileDatabase = IndividualProjectApplication.myFileDatabase;
    assertEquals(1, myFileDatabase.getDepartmentMapping().size());

    // set the database back to the original value
    IndividualProjectApplication.overrideDatabase(testMyFileDatabase);
  }

  @Test
  public void onTerminationTest() {
    application.onTermination();
    assertEquals(7, IndividualProjectApplication.myFileDatabase.getDepartmentMapping().size());
  }
  
  private static IndividualProjectApplication application;
}
