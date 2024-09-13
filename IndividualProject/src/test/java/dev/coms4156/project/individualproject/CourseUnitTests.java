package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
*    Constructs a new Course object with the given parameters. Initial count starts at 0.
*
@SpringBootTest
/**
    Constructs a new Course object with the given parameters. Initial count starts at 0.
*
@ContextConfiguration

/**
    Constructs a new Course object with the given parameters. Initial count starts at 0.
*/
public class CourseUnitTests {
  /**
  * Constructs a new Course object with the given parameters. Initial count starts at 0.
  */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
  * Constructs a new Course object with the given parameters. Initial count starts at 0.
  */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

