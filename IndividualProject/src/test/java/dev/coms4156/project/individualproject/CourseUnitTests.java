package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class is built to test the Course class, which we have created to represent 
 * a course within an educational institution.
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

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Griffin BBB");
    assertEquals("Griffin BBB", testCourse.getInstructorName());
    // set the instructor name back to the original value
    testCourse.reassignInstructor("Griffin Newbold");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("417 BBB");
    assertEquals("417 BBB", testCourse.getCourseLocation());
    // set the location back to the original value
    testCourse.reassignLocation("417 IAB");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("1:00-2:15");
    assertEquals("1:00-2:15", testCourse.getCourseTimeSlot());
    // set the time back to the original value
    testCourse.reassignTime("11:40-12:55");
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(251);
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    testCourse.enrollStudent();
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(250);
    testCourse.dropStudent();
    assertEquals(false, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

