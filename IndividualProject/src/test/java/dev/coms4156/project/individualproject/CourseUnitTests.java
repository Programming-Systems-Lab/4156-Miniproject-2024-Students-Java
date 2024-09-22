package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * This class contains unit tests for the Course class, primarily testing the
 * functionality of the various methods.
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

  /**
   * Tests the enrollStudent method to ensure that students can be enrolled in the course.
   */
  @Test
  public void enrollStudentTest() {
    int initialCount = testCourse.getEnrolledStudentCount();
    testCourse.enrollStudent();
    assertEquals(initialCount + 1, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests the dropStudent method to ensure that students can be dropped from the course.
   */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(5);  // Set a known student count
    int initialCount = testCourse.getEnrolledStudentCount();
    testCourse.dropStudent();
    assertEquals(initialCount - 1, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests the getCourseLocation method to verify it returns the correct course location.
   */
  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  /**
   * Tests the getInstructorName method to verify it returns the correct instructor name.
   */
  @Test
  public void getInstructorNameTest() {
    assertEquals("G N", testCourse.getInstructorName());
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
   */
  @Test
  public void reassignInstructorTest() {
    String newInsName = "New Instructor";
    testCourse.reassignInstructor(newInsName);
    assertEquals(newInsName, testCourse.getInstructorName());
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
   * Tests the isCourseFull method to verify it correctly checks if the course is full.
   */
  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);  // Set the student count to match capacity
    assertFalse(testCourse.isCourseFull());

    testCourse.setEnrolledStudentCount(260);  // Set the student count over the capacity
    assertTrue(testCourse.isCourseFull());
  }
}
