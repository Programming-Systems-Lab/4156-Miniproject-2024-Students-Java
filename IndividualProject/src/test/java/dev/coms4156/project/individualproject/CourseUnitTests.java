package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;


/**
 * Class that contains all of the tests for every method.c
 */
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Set up the preliminary courses for setup that are used to test.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse2 = new Course("Luci Feinberg", "417 IAB", "11:40-12:55", 1000);
    testCourse3 = new Course("Luci Feinberg", "417 IAB", "11:40-12:55", -10);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentCountTest() {
    Boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentCountTest2() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse2.enrollStudent());
  }

  @Test
  public void enrollStudentCountTest3() {
    Boolean expectedResult = false;
    assertEquals(expectedResult, testCourse3.enrollStudent());
  }

  @Test
  public void isCourseFullTest() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }


  @Test
  public void isCourseFullTest2() {
    Boolean expectedResult = false;
    assertEquals(expectedResult, testCourse2.isCourseFull());
  }

  @Test
  public void isCourseFullTest3() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse3.isCourseFull());
  }

  @Test
  public void dropStudentTests() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.dropStudent());
  }

  @Test
  public void dropStudentTests2() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse2.dropStudent());
  }

  @Test
  public void dropStudentTests3() {
    Boolean expectedResult = true;
    assertEquals(expectedResult, testCourse3.dropStudent());
  }

  @Test
  public void dropStudentTests4() {
    Boolean expectedResult = false;
    Course course = new Course("Luci Feinberg", "417 IAB", "11:40-12:55", 50);
    course.setEnrolledStudentCount(0);
    assertEquals(expectedResult, course.dropStudent());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testCourse2;
  public static Course testCourse3;

}