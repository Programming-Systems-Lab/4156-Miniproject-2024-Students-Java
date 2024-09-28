package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;



/**
 * Unit tests for the Course class.
 */

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
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
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Missy Elliot");
    String expectedResult = "Missy Elliot";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("Outer Space");
    String expectedResult = "Outer Space";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("1:00-1:01");
    String expectedResult = "1:00-1:01";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(33);
    int expectedResult = 33;
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }
  
  /** The test course instance used for testing. */
  public static Course testCourse;
}

