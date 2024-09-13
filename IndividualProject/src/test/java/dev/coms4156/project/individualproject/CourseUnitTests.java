package dev.coms4156.project.individualproject;

// import org.junit.jupiter.api.*;
// import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
*/
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * setupCourseForTesting.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(100);
    testCourse2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse2.setEnrolledStudentCount(250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testEnrollStudent() {
    boolean result;
    result = testCourse.enrollStudent();
    assertTrue(result);
  }

  @Test
  public void testEnrollStudentFalse() {
    testCourse2.setEnrolledStudentCount(250);
    boolean result;
    result = testCourse2.enrollStudent();
    assertFalse(result);
  }
  /** The test course instance used for testing. */
  
  public static Course testCourse;
  public static Course testCourse2;
}