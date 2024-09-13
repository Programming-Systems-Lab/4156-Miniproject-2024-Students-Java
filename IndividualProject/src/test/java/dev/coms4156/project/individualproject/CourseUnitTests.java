package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Course} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Course} class
 * methods.
 */
public class CourseUnitTests {

  /** The test course instance used for testing. */
  private Course testCourse;

  @BeforeEach
  public void setUp() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void constructorTestValidConstructor() {
    Course course = new Course("Gail Kaiser", "417 IAB", "11:40-12:55", 30);
    assertNotNull(course);
  }

  @Test
  public void constructorTestInvalidInstructorName() {
    assertThrows(
        IllegalArgumentException.class, () -> new Course(null, "417 IAB", "11:40-12:55", 30));
  }

  @Test
  public void constructorTestInvalidCourseLocation() {
    assertThrows(
        IllegalArgumentException.class, () -> new Course("Gail Kaiser", "", "11:40-12:55", 30));
  }

  @Test
  public void constructorTestInvalidTimeSlot() {
    assertThrows(
        IllegalArgumentException.class, () -> new Course("Gail Kaiser", "11:40-12:55", null, 30));
  }

  @Test
  public void constructorTestInvalidCapacity() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Course("Gail Kaiser", "417 IAB", "11:40-12:55", -1));
  }

  @Test
  public void toStringReturnsCourseParamsAsString() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentWhenCourseIsNotFull() {
    testCourse.setEnrolledStudentCount(0);
    assertTrue(testCourse.enrollStudent());
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrollStudentWhenCourseIsFull() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(
        testCourse.enrollStudent(), "Course is full, cannot add a student to a full course");
  }

  @Test
  public void setEnrollStudentCountUpdateEnrolledStudentCount() {
    testCourse.setEnrolledStudentCount(200);
    assertEquals(200, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void setEnrollStudentCountNonNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.setEnrolledStudentCount(-1),
        "Expected non-negative value");
  }

  @Test
  public void dropStudentWhenStudentsAreEnrolled() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentWhenStudentsAreNotEnrolled() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void reassignInstructorUpdateInstructorName() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals("Gail Kaiser", testCourse.getInstructorName());
  }

  @Test
  public void reassignInstructorExceptionForEmptyStringInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignInstructor(""),
        "Expected a IllegalArgumentException due to empty string value for instructor name.");
  }

  @Test
  public void reassignInstructorExceptionForNullInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignInstructor(null),
        "Expected a IllegalArgumentException due to null value for instructor name.");
  }

  @Test
  public void reassignLocationExceptionForEmptyStringValue() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignLocation(""),
        "Expected a non-empty string for location name.");
  }

  @Test
  public void reassignLocationExceptionForNullValue() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignLocation(null),
        "Expected a non-null value for location name.");
  }

  @Test
  public void reassignTimeValidTimeFormat() {
    testCourse.reassignTime("2:40-4:55");
    assertEquals("2:40-4:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignTimeInvalidTimeFormat() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> testCourse.reassignTime("invalid-time"));
    String expectedMessage =
        "Invalid time format.  "
            + "Expected format: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or 'HH:MM-HH:MM'.  "
            + "Also ensure valid hours (00-23) and minutes (00-59).";
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  public void isValidTimeSlotTestValidTimeSlot() {
    assertTrue(Course.isValidTimeSlot("9:00-17:30"));
    assertTrue(Course.isValidTimeSlot("09:00-17:30"));
    assertTrue(Course.isValidTimeSlot("9:00-9:30"));
    assertTrue(Course.isValidTimeSlot("09:00-09:30"));
  }

  @Test
  public void isValidTimeSlotTestInvalidTimeSlot() {
    assertFalse(Course.isValidTimeSlot("9:00-25:00")); // Invalid hour
    assertFalse(Course.isValidTimeSlot("09:00-17:60")); // Invalid minute
    assertFalse(Course.isValidTimeSlot("9:00-17:30-20:00")); // Incorrect format
    assertFalse(Course.isValidTimeSlot("")); // Empty string
    assertFalse(Course.isValidTimeSlot(null)); // Null input
  }
}
