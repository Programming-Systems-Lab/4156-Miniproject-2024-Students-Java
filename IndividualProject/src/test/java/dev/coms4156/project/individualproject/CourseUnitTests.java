package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Course class.
 * 
 */

// @SpringBootTest
// @ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Missy Elliot; Location: 333 Pineapple Street; Time: 1:00-2:00";
    assertEquals(expectedResult, testCourse.toString());
  }


  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Missy Elliot");
    assertEquals("Missy Elliot", testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("333 Pineapple Street");
    assertEquals("333 Pineapple Street", testCourse.getCourseLocation());
  }

  @Test 
  public void reassignTimeTest() {
    testCourse.reassignTime("1:00-2:00");
    assertEquals("1:00-2:00", testCourse.getTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(33);
    assertEquals(33, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrolledStudentCountTest() {
    testCourse.enrollStudent();
    assertEquals(34, testCourse.getEnrolledStudentCount());
    testCourse.dropStudent();
    assertEquals(33, testCourse.getEnrolledStudentCount());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}

