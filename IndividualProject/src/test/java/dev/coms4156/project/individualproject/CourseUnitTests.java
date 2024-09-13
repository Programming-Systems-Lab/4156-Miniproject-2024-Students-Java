package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is used to test toString method of Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**This is pre-work to initialize before tests.*/
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testReassignInstructor = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testReassignTime = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testEnrollStudent = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 600);
    testDropStudent =  new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 600);
    testSetEnrolledStudentCount = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 600);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /** Implemented tests start here.*/
  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Ashley Zhang";
    testReassignInstructor.reassignInstructor("Ashley Zhang");
    assertEquals(expectedResult, testReassignInstructor.getInstructorName());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "12:40-15:55";
    testReassignTime.reassignTime("12:40-15:55");
    assertEquals(expectedResult, testReassignTime.getCourseTimeSlot());
  }

  @Test
  public void enrollStudentTest() {
    for (int i = 0; i < 100; i++) {
      assertTrue(testEnrollStudent.enrollStudent());
    }
    assertTrue(testEnrollStudent.isCourseFull());
    assertFalse(testEnrollStudent.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    for (int i = 0; i < 500; i++) {
      assertTrue(testDropStudent.dropStudent());
    }
    assertFalse(testDropStudent.isCourseFull());
    assertFalse(testDropStudent.dropStudent());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testSetEnrolledStudentCount.setEnrolledStudentCount(400);
    for (int i = 0; i < 200; i++) {
      assertTrue(testSetEnrolledStudentCount.enrollStudent());
    }
    assertTrue(testSetEnrolledStudentCount.isCourseFull());
    assertFalse(testSetEnrolledStudentCount.enrollStudent());
  }





  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testReassignInstructor;
  public static Course testReassignTime;
  public static Course testsetEnrolledStudentCount;
  public static Course testEnrollStudent;
  public static Course testDropStudent;
  public static Course testSetEnrolledStudentCount;

}

