package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  public static Course testCourse;

  /**
   * Set up before all tests.
   * Initializes the test course object with sample data.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Test the toString method.
   * Ensures that the string representation of the course is correct.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString(), "toString() method failed");
  }

  /**
   * Tests the enrollStudent() method to ensure the student count is incremented correctly.
   * Validates both the return value and the internal state of the Course object.
   */
  @Test
  public void enrollStudentTest() {
    int initialCount = getEnrolledStudentCountReflection();
    boolean result = testCourse.enrollStudent();
    assertFalse(result, "enrollStudent() should always return false as per current implementation");
    assertEquals(initialCount + 1, getEnrolledStudentCountReflection(), "Student count should have incremented by 1");
  }

  /**
   * Tests the dropStudent() method to ensure the student count is decremented correctly.
   * Validates both the return value and the internal state of the Course object.
   */
  @Test
  public void dropStudentTest() {
    int initialCount = getEnrolledStudentCountReflection();
    boolean result = testCourse.dropStudent();
    assertFalse(result, "dropStudent() should always return false as per current implementation");
    assertEquals(initialCount - 1, getEnrolledStudentCountReflection(), "Student count should have decremented by 1");
  }

  /**
   * Test the getCourseLocation method.
   * Note: This method incorrectly returns the instructor name. The test will check the current behavior.
   */
  @Test
  public void getCourseLocationTest() {
    assertEquals("Griffin Newbold", testCourse.getCourseLocation(), "getCourseLocation() returns the instructor's name as per current implementation");
  }

  /**
   * Test the getInstructorName method.
   * Note: This method incorrectly returns the course location. The test will check the current behavior.
   */
  @Test
  public void getInstructorNameTest() {
    assertEquals("417 IAB", testCourse.getInstructorName(), "getInstructorName() returns the course location as per current implementation");
  }

  /**
   * Tests the isCourseFull() method with different enrolled student counts.
   * Covers scenarios where the course is at capacity, below capacity, and over capacity.
   */
  @Test
  public void isCourseFullTest() {
    // At capacity (250)
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.isCourseFull(), "isCourseFull() should return false when enrollment equals capacity");

    // Below capacity
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.isCourseFull(), "isCourseFull() should return false when enrollment is less than capacity");

    // Over capacity
    testCourse.setEnrolledStudentCount(300);  // Over the capacity
    assertTrue(testCourse.isCourseFull(), "isCourseFull() should return true when enrollment is greater than capacity");
  }

  /**
   * Test the reassignInstructor method.
   * Ensures that the instructor is reassigned correctly.
   */
  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Jane Smith");
    assertEquals("Jane Smith", testCourse.getInstructorName(), "reassignInstructor() method failed");
  }

  /**
   * Test the reassignLocation method.
   * Ensures that the course location is reassigned correctly.
   */
  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("402 MUDD");
    assertEquals("402 MUDD", testCourse.getCourseLocation(), "reassignLocation() method failed");
  }

  /**
   * Test the reassignTime method.
   * Ensures that the course time slot is reassigned correctly.
   */
  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("2:00-3:15");
    assertEquals("2:00-3:15", testCourse.getCourseTimeSlot(), "reassignTime() method failed");
  }

  /**
   * Helper method to retrieve the enrolled student count using reflection.
   * This is necessary because the enrolledStudentCount field is private and there is no getter method.
   *
   * @return The current enrolled student count.
   */
  private int getEnrolledStudentCountReflection() {
    try {
      java.lang.reflect.Field field = Course.class.getDeclaredField("enrolledStudentCount");
      field.setAccessible(true);
      return field.getInt(testCourse);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Reflection error: Unable to access enrolledStudentCount", e);
    }
  }
}
