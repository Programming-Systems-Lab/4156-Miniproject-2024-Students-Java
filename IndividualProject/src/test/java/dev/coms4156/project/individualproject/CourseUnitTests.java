package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class implements Unit Tests for the {@link Course} class.
 *
 * <p>This includes testing the enrollStudent and dropStudent methods.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * This function tests the {@code enrollStudentSuccess()} method.
   *
   * <p>If the course student enrollment is less than the capacity of the class, this method should return true.
   */
  @Test
  public void enrollStudentSuccess() {
    assertTrue(testCourse.enrollStudent());
  }

  /**
   * This function tests the {@code enrollStudentSuccess()} method.
   *
   * <p>If the course student enrollment is greater than the capacity of the class, this method should return false.
   */
  @Test
  public void enrollStudentFailure() {
    testCourse.setEnrolledStudentCount(testCourse.getCourseCapacity()+1);
    assertFalse(testCourse.enrollStudent());
  }

  /**
   * This function tests the {@code dropStudent()} method.
   *
   * <p>If the course student enrollment greater than 0, this method should return true.
   */
  @Test
  public void dropStudentSuccess() {
    assertTrue(testCourse.dropStudent());
  }

  /**
   * This function tests the {@code dropStudent()} method.
   *
   * <p>If the course student enrollment 0 (or somehow lower), this method should return false.
   */
  @Test
  public void dropStudentFailure() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}

