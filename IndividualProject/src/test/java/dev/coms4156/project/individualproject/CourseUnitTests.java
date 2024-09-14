package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains the tests for the {@link Course} class.
 * The tests verify the correctness of the Course class's methods.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;

  /**
   * Initializes a Course instance to be used across all tests in this class.
   * This method runs once before all the test methods are executed.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course(
            "Griffin Newbold",
            "417 IAB",
            "11:40-12:55",
            250);
  }

  /**
   * Tests the {@link Course#toString()} method.
   */
  @Test
  public void toStringTest() {
    setupCourseForTesting();
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Tests the {@link Course#reassignInstructor(String)} method.
   */
  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals("Gail Kaiser", testCourse.getInstructorName());
  }

  /**
   * Tests the {@link Course#reassignLocation(String)} method.
   */
  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("Butler Library");
    assertEquals("Butler Library", testCourse.getCourseLocation());
  }

  /**
   * Tests the {@link Course#reassignTime(String)} method.
   */
  @Test
  public void reassignTimeTest() {
    setupCourseForTesting();
    testCourse.reassignTime("14:40-15:55");
    assertEquals("14:40-15:55", testCourse.getCourseTimeSlot());
  }

  /**
   * Tests the {@link Course#setEnrolledStudentCount(int)} method.
   */
  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(500);
    assertTrue(testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(100);
    assertFalse(testCourse.isCourseFull());
  }

  /**
   * Tests the {@link Course#dropStudent()} method.
   */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.dropStudent());
    assertFalse(testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  /**
   * Tests the {@link Course#enrollStudent()} method.
   */
  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
    testCourse.setEnrolledStudentCount(0);
    assertTrue(testCourse.enrollStudent());
  }
}

