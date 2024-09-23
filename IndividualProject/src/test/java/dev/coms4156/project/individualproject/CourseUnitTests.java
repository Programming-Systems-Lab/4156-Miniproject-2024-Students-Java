package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
   * Sets up a Course object for testing.
   * This method is run once before all tests.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("G N", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Tests the constructor of the Course class.
   * Case for invalid capacity.
   */

  @Test
  public void invalidCapacity() {
    assertThrows(IllegalArgumentException.class, () -> new Course("G N", "417 IAB", "11:40-12:55", -1));
  }

  /**
   * Tests the constructor of the Course class.
   * Case for invalid location.
   */

  @Test
  public void invalidLocation() {
    assertThrows(IllegalArgumentException.class, () -> new Course("G N", "4", "11:40-12:55", 100));
  }

  /**
   * Tests the constructor of the Course class.
   * Case for invalid instructor name.
   */

  @Test
  public void invalidInstructorName() {
    assertThrows(IllegalArgumentException.class, () -> new Course("G", "417 IAB", "11:40-12:55", 100));
  }

  /**
   * Tests the constructor of the Course class.
   * Case for invalid time.
   */

  @Test
  public void invalidTime() {
    assertThrows(IllegalArgumentException.class, () -> new Course("G", "417 IAB", "1", 100));
  }

  /**
   * Tests the toString method of the Course class.
   * This test checks if the toString method returns the correct string
   * representation of the course.
   */
  @Test
  public void toStringTest() {
    String expectedResult =
          "\nInstructor: G N; Location: 417 IAB; Time: 11:40-12:55; Capacity: 250; Enrollment: 500";
    assertEquals(expectedResult, testCourse.toString());
  }


  @Test
  public void enrollStudentTest() {
    int initialCount = testCourse.getEnrolledStudentCount();
    boolean response = testCourse.enrollStudent();
    assertTrue(response);
    assertEquals(initialCount + 1, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests the dropStudent method by dropping a student from the course.
   * Checks that the new student count decreased by one.
   */
  @Test
  public void dropStudentTest() {
    int initialCount = testCourse.getEnrolledStudentCount();
    boolean response = testCourse.dropStudent();
    assertTrue(response);
    assertEquals(initialCount - 1, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests the dropStudent method by dropping a student from the course.
   * Checks that the new student count decreased by one.
   */
  @Test
  public void dropStudentBelowZeroTest() {
    testCourse.setEnrolledStudentCount(0);
    boolean result = testCourse.dropStudent();
    assertFalse(result);
  }

  /**
   * Tests the getCourseLocation method by checking if it returns the correct course location.
   */
  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB",
          testCourse.getCourseLocation());
  }

  /**
   * Tests the getInstructorName method by checking the instructor name matches initialized value.
   */
  @Test
  public void getInstructorNameTest() {
    assertEquals("G N",
          testCourse.getInstructorName());
  }

  /**
   * Tests the getCourseTimeSlot method to verify it returns the correct course time slot.
   */
  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  /**
   * Tests the reassignInstructor method to ensure the instructor name can be updated.
   * Case where instructor is invalid.
   */
  @Test
  public void reassignInstructorTestInvalidInstructor()  {
    String newInsName = "N";
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignInstructor(newInsName));
  }

  /**
   * Tests the reassignInstructor method to ensure the instructor name can be updated.
   */
  @Test
  public void reassignInstructorTest() {
    String newInsName = "New Instructor";
    testCourse.reassignInstructor(newInsName);
    assertEquals(newInsName, testCourse.getInstructorName());
  }

  /**
   * Tests the reassignLocation method to ensure the course location can be updated.
   * Case where location is invalid.
   */
  @Test
  public void reassignLocationTestInvalidLocation()  {
    String newLocation = "N";
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignLocation(newLocation));
  }

  /**
   * Tests the reassignLocation method to ensure the course location can be updated.
   */
  @Test
  public void reassignLocationTest() {
    String newLocation = "New Location";
    testCourse.reassignLocation(newLocation);
    String currLocation = testCourse.getCourseLocation();
    assertEquals(newLocation, currLocation);
  }

  /**
   * Tests the reassignTime method to ensure the course time can be updated.
   * Case where location is invalid.
   */
  @Test
  public void reassignTimeTestInvalidTime()  {
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignTime("1"));
  }

  /**
   * Tests the reassignTime method to ensure the course time can be updated.
   */
  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("10:00-11:15");
    assertEquals("10:00-11:15", testCourse.getCourseTimeSlot());
  }

  /**
   * Tests the setEnrolledStudentCount method to ensure the student count can be updated.
   */
  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(100);
    assertEquals(100, testCourse.getEnrolledStudentCount());

  }

  /**
   * Tests the setEnrolledStudentCount method to ensure the student count can be updated.
   * Case where new count is below 0.
   */
  @Test()
  public void setEnrolledStudentCountTestBelowZero() {
    // Test negative values
    assertThrows(IllegalArgumentException.class, () -> testCourse.setEnrolledStudentCount(-100));
  }

  /**
   * Tests the isCourseFull method to verify it correctly checks if the course is full.
   */
  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);  // Match the capacity
    assertFalse(testCourse.isCourseFull());

    testCourse.setEnrolledStudentCount(260);  // Set count over the max capacity
    assertTrue(testCourse.isCourseFull());
  }
}
