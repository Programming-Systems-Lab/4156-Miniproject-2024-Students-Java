package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains all the unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Set up the department and course objects for testing.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    // Create a Course object for testing
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);

    // Initialize a HashMap with one course
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("CS101", testCourse);

    // Create a Department object with initial values
    testDepartment = new Department("CS", courses, "Dr. Smith", 50);
  }

  @Test
  public void constructorTest() {
    assertEquals("Dr. Smith", testDepartment.getDepartmentChair());
    assertEquals(50, testDepartment.getNumberOfMajors());
    assertTrue(testDepartment.getCourseSelection().containsKey("CS101"));
    assertEquals(testCourse, testDepartment.getCourseSelection().get("CS101"));
  }

  @Test
  public void toStringTest() {
    String expectedResult = "CS CS101: \n" +
            "Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(50, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Dr. Smith", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(testCourse, testDepartment.getCourseSelection().get("CS101"));
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(51, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(49, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course newCourse = new Course("Jane Doe", "123 Main St", "09:00-10:15", 30);
    testDepartment.addCourse("CS102", newCourse);
    assertTrue(testDepartment.getCourseSelection().containsKey("CS102"));
    assertEquals(newCourse, testDepartment.getCourseSelection().get("CS102"));
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("CS103", "Alice Brown", "202 Lab", "15:00-16:15", 40);
    Course newCourse = testDepartment.getCourseSelection().get("CS103");
    assertNotNull(newCourse);
    assertEquals("Alice Brown", newCourse.getInstructorName());
    assertEquals("202 Lab", newCourse.getCourseLocation());
    assertEquals("15:00-16:15", newCourse.getCourseTimeSlot());
  }


  public static Course testCourse;
  public static Department testDepartment;
}
