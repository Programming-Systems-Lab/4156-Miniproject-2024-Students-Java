package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Department Unit Tests.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Static instance of the Department used for testing.
   */
  public static Department testDepartment;

  /**
   * Static instance of the Course used for testing.
   */
  public static Course testCourse;

  /**
   * Initializes a Department and Course instance before each test.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    testCourse = new Course("Jae Woo Lee", "417 IAB", "1:10-2:25", 250);
    HashMap<String, Course> courses = new HashMap<>();
    testDepartment = new Department("COMS", courses, "Brian Borowski", 100);
  }

  /**
   * Tests if the number of majors in the department is returned correctly.
   */
  @Test
  public void testGetNumberOfMajors() {
    assertEquals(100, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests if the department chair is returned correctly.
   */
  @Test
  public void testGetDepartmentChair() {
    assertEquals("Brian Borowski", testDepartment.getDepartmentChair());
  }

  /**
   * Tests if the course selection list is initialized as empty.
   */
  @Test
  public void testGetCourseSelection() {
    assertTrue(testDepartment.getCourseSelection().isEmpty());
  }

  /**
   * Tests if a person can be added to the list of majors and the number increases by 1.
   */
  @Test
  public void testAddPersonToMajor() {
    testDepartment.addPersonToMajor();
    assertEquals(101, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests if a person can be removed from the list of majors and the number decreases by 1.
   */
  @Test
  public void testDropPersonFromMajor() {
    testDepartment.dropPersonFromMajor();
    assertEquals(99, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests if removing a person from the list of majors when there are zero majors
   * does not result in negative numbers.
   */
  @Test
  public void testDropPersonFromMajorAtZero() {
    testDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", 0);
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests if a course can be added to the department's course selection.
   */
  @Test
  public void testAddCourse() {
    testDepartment.addCourse("3157", testCourse);
    assertTrue(testDepartment.getCourseSelection().containsKey("3157"));
    assertEquals(testCourse, testDepartment.getCourseSelection().get("3157"));
  }

  /**
   * Tests if a new course can be created within the department with the given details.
   */
  @Test
  public void testCreateCourse() {
    testDepartment.createCourse("3157", "Jae Woo Lee", "417 IAB", "1:10-2:25", 25);
    Course course = testDepartment.getCourseSelection().get("3157");
    assertEquals("Jae Woo Lee", course.getInstructorName());
    assertEquals("417 IAB", course.getCourseLocation());
    assertEquals("1:10-2:25", course.getCourseTimeSlot());
  }

  /**
   * Tests the string representation of the department including its courses.
   */
  @Test
  public void testToString() {
    testDepartment.addCourse("3157", testCourse);
    String expected = "COMS 3157: " + testCourse.toString() + "\n";
    assertEquals(expected, testDepartment.toString());
  }

  /**
   * Tests if the number of majors does not decrease below zero even if a negative number is used.
   */
  @Test
  public void testDropPersonFromMajorNegativeNumberOfMajors() {
    testDepartment = new Department("COMS", new HashMap<>(), "Jae Woo Lee", -5);
    testDepartment.dropPersonFromMajor();
    assertEquals(-5, testDepartment.getNumberOfMajors()); // Should not decrease
  }
}
