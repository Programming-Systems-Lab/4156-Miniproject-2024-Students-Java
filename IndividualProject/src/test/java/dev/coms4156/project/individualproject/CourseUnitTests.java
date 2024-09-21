package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
/**
 * Unit tests for the Course class.
 * This class contains unit tests for the Course class, primarily testing the
 * functionality of the toString method.
 */

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Sets up a Course object for testing.
   * This method is run once before all tests.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Tests the toString method of the Course class.
   * This test checks if the toString method returns the correct string
   * representation of the course.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}
