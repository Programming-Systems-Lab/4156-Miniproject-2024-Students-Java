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
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }
}

