package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is used to test methods of Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**This is pre-work to initialize before tests.*/
  @BeforeAll
  public static void setupDepartmentForTesting() {

    courses = new HashMap<>();
    courses.put("2500", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
    courses.put("3000", new Course("David", "Room 102", "11:00-12:00", 40));
    departmentCS = new Department("CS", courses, "Dr. Adams", 2);
    departmentEcon = new Department("Econ", coursesEcon, "Dr. A", 2222);
  }


  @Test
  public void toStringTest() {
    String expectedResult =
            "CS 2500: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "CS 3000: \nInstructor: David; Location: Room 102; Time: 11:00-12:00\n";
    assertEquals(expectedResult, departmentCS.toString());
  }

  @Test
  public void createCourseTest() {
    departmentEcon.createCourse("2000", "Bob", "Room 222", "15:40-17:55", 60);
    assertEquals(1, departmentEcon.getCourseSelection().size());
  }

  @Test
  public void addPersonToMajorTest() {
    departmentEcon.addPersonToMajor();
    assertEquals(2223, departmentEcon.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    departmentCS.dropPersonFromMajor();
    assertEquals(1, departmentCS.getNumberOfMajors());
    for (int i = 0; i < 2; i++) {
      departmentCS.dropPersonFromMajor();
    }
    assertEquals(0, departmentCS.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Dr. A", departmentEcon.getDepartmentChair());
  }



  /** The test course instance used for testing. */

  private static Department departmentCS;
  private static Department departmentEcon;
  private static HashMap<String, Course> courses;
  private static HashMap<String, Course> coursesEcon;

}

