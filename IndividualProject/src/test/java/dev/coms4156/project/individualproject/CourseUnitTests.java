package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit testing for the Course Class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void enrollStudentTest() {
    assertEquals(true, testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    assertEquals(true, testCourse.dropStudent());
  }

  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignInstructorTest() {
    String newInstructor = "Bryan Granda";
    testCourse.reassignInstructor(newInstructor);
    assertEquals(newInstructor, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String newLocation = "501 HAV";
    testCourse.reassignLocation(newLocation);
    assertEquals(newLocation, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String newTime = "10:10-11:25";
    testCourse.reassignTime(newTime);
    assertEquals(newTime, testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(false, testCourse.isCourseFull());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}
