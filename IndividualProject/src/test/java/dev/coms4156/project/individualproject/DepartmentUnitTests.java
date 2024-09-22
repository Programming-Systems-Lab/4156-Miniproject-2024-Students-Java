package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /** The test department instance used for testing. */
  public static Department testDepartment;
  public static HashMap<String, Course> testCourses;

  /**
   * Sets up a Department object for testing.
   * Runs once before each test is conducted.
   */
  @BeforeEach
  public void setupForTesting() {
    testCourses = new HashMap<>();
    testCourses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    testDepartment = new Department("COMS", testCourses, "Luca Carloni", 2700);
  }

  /**
   * Tests the getNumberOfMajors method to ensure it returns the correct number of majors.
   */
  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the getDepartmentChair method and verifies that it returns the correct department chair.
   */
  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  /**
   * Tests that getCourseSelection method returns the appropriate course selection.
   */
  @Test
  public void getCourseSelectionTest() {
    assertEquals(testCourses, testDepartment.getCourseSelection());
  }

  /**
   * Tests the createCourse method by creating a new course and adding it to the department.
   */
  @Test
  public void createCourseTest() {
    testDepartment.createCourse("3157", "Jae Lee", "417 IAB", "4:10-5:25", 400);
    Course createdCourse = testDepartment.getCourseSelection().get("3157");

    // Verify that the course was successfully created and added
    assertNotNull(createdCourse);
    assertEquals("Jae Lee", createdCourse.getInstructorName());
    assertEquals("417 IAB", createdCourse.getCourseLocation());
    assertEquals("4:10-5:25", createdCourse.getCourseTimeSlot());
    assertEquals(400, createdCourse.getEnrollmentCapacity());
  }

  /**
   * Tests the addPersonToMajor method by incrementing the number of majors.
   */
  @Test
  public void addPersonToMajorTest() {
    int initialCount = testDepartment.getNumberOfMajors();
    testDepartment.addPersonToMajor();
    assertEquals(initialCount + 1, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the dropPersonFromMajor method by decrementing the number of majors.
   * The updated value is then verified.
   */
  @Test
  public void dropPersonFromMajorTest() {
    int initialCount = testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    assertEquals(initialCount - 1, testDepartment.getNumberOfMajors());
  }
}
