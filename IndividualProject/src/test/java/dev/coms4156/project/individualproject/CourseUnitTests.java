package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the {@link Course} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Course} class
 * methods.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  public static Course testCourse;

  @BeforeEach
  public void Course_setUp() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void Course_toString_ReturnsCourseParamsAsString() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void Course_enrollStudent_WhenCourseIsNotFull() {
    testCourse.setEnrolledStudentCount(0);
    assertTrue(testCourse.enrollStudent());
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void Course_enrollStudent_WhenCourseIsFull() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(
        testCourse.enrollStudent(), "Course is full, cannot add a student to a full course");
  }

  @Test
  public void Course_setEnrollStudentCount_UpdateEnrolledStudentCount() {
    testCourse.setEnrolledStudentCount(200);
    assertEquals(200, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void Course_setEnrollStudentCount_NonNegative() {

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          testCourse.setEnrolledStudentCount(-1);
        },
        "Expected non-negative value");
  }

  @Test
  public void Course_dropStudent_WhenStudentsAreEnrolled() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void Course_dropStudent_WhenStudentsAreNotEnrolled() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void Course_reassignInstructor_UpdateInstructorName() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals("Gail Kaiser", testCourse.getInstructorName());
  }

  @Test
  public void Course_reassignInstructor_CheckForValidInstructorName() {
    String variableType = "instructor name";
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          testCourse.reassignInstructor("");
        },
        String.format("Expected an empty string for %s when validating input.", variableType));

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          testCourse.reassignInstructor(null);
        },
        String.format("Expected a null value for %s when validating input.", variableType));
  }

  @Test
  public void Course_reassignLocation_CheckForValidLocationName() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          testCourse.reassignLocation("");
        },
        "Expected a non-empty string for location name.");

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          testCourse.reassignLocation(null);
        },
        "Expected a non-null value for location name.");
  }

  @Test
  public void Course_reassignTime_ValidTimeFormat() {
    testCourse.reassignTime("2:40-4:55");
    assertEquals("2:40-4:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void Course_reassignTime_InvalidTimeFormat() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> testCourse.reassignTime("invalid-time"));
    String expectedMessage =
        "Invalid time format. Expected format: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or 'HH:MM-HH:MM'.";
    assertEquals(expectedMessage, exception.getMessage());
  }
}
