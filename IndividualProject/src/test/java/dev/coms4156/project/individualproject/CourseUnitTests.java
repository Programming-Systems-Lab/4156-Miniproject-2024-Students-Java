package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Sets the enrollment count for a department based on the provided department code.
 *
 * @param deptCode The code of the department whose enrollment count is to be set.
 * @return A response entity indicating the result of the operation.
 */
  
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Test for the toString() method.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Test for the constructor of the Course class.
   */
  @Test
  public void constructorTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
    assertEquals("417 IAB", testCourse.getCourseLocation());
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  /**
   * Test for the enrollStudent() method.
   */
  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    assertEquals(true, testCourse.enrollStudent());
  }

  /**
   * Test for the dropStudent() method.
   */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    assertEquals(true, testCourse.dropStudent());
  }

  /**
   * Test for the reassignInstructor() method.
   */
  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Ben");
    assertEquals("Ben", testCourse.getInstructorName());
    testCourse.reassignInstructor("Griffin Newbold");

  }

  /**
   * Test for the reassignLocation() method.
   */
  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("House");
    assertEquals("House", testCourse.getCourseLocation());
    testCourse.reassignLocation("417 IAB");

  }

  /**
   * Test for the reassignTime() method.
   */
  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("1:00pm");
    assertEquals("1:00pm", testCourse.getCourseTimeSlot());
    testCourse.reassignTime("11:40-12:55");

  }

  /**
   * Test for the isCourseFull() method.
   */
  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertEquals(true, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(1);
    assertEquals(false, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}
