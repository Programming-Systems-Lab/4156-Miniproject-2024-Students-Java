package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Course unit tests.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Sets course for testing.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * To string test.
   */
  @Test
  public void toStringTest_success() {
    testCourse.reassignInstructor("Griffin Newbold");
    testCourse.reassignLocation("417 IAB");
    testCourse.reassignTime("11:40-12:55");
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Enroll student test zero students enrolled success.
   */
  @Test
  public void enrollStudentTest_zeroStudentsEnrolled_success() {
    assertTrue(testCourse.enrollStudent());
    assertEquals(testCourse.getEnrolledStudentCount(), 1);
  }

  /**
   * Drop student test one student enrolled success.
   */
  @Test
  public void dropStudentTest_oneStudentEnrolled_success() {
    assertEquals(testCourse.getEnrolledStudentCount(), 1);
    assertTrue(testCourse.dropStudent());
  }

  /**
   * Drop student test zero students enrolled fail.
   */
  @Test
  public void dropStudentTest_zeroStudentsEnrolled_fail() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  /**
   * Reassign instructor test success.
   */
  @Test
  public void reassignInstructorTest_success() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals(testCourse.getInstructorName(), "Gail Kaiser");
  }

  /**
   * Reassign location reassign time test success.
   */
  @Test
  public void reassignLocation_reassignTimeTest_success() {
    testCourse.reassignLocation("717 HAM");
    testCourse.reassignTime("1:10-2:25");
    assertEquals(testCourse.getCourseLocation(), "717 HAM");
    assertEquals(testCourse.getCourseTimeSlot(), "1:10-2:25");
  }

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

