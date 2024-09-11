package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the IndividualProjectApplication class.
 *
 * <p>Tests include verifying the toString method of the Course and
 * Department classes.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Defines and initializes variables for a new test course.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course(instructorName, courseLocation, timeSlot, capacity);
  }

  @Test
  public void enrollStudentTest() {
    int pre = testCourse.getEnrolledStudentCount();
    boolean result = testCourse.enrollStudent();
    int post = testCourse.getEnrolledStudentCount();
    assertEquals(++pre, post);
    assertTrue(result);
  }

  @Test
  public void dropStudentTest() {
    int pre = testCourse.getEnrolledStudentCount();
    boolean result = testCourse.dropStudent();
    int post = testCourse.getEnrolledStudentCount();
    assertEquals(--pre, post);
    assertTrue(result);
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals(courseLocation, testCourse.getCourseLocation());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    int newCount = 100;
    testCourse.setEnrolledStudentCount(newCount);
    assertEquals(newCount, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void getInstructorNameTest() {
    assertEquals(instructorName, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlot() {
    assertEquals(timeSlot, testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    String newInstructor = "Sam Edwards";
    testCourse.reassignInstructor(newInstructor);
    assertEquals(newInstructor, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String newLocation = "309 HAV";
    testCourse.reassignLocation(newLocation);
    assertEquals(newLocation, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTime() {
    String newTime = "8:40-9:55";
    testCourse.reassignTime(newTime);
    assertEquals(newTime, testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTest() {
    int over = 600;
    testCourse.setEnrolledStudentCount(over);
    assertTrue(testCourse.isCourseFull());

    int under = 100;
    testCourse.setEnrolledStudentCount(under);
    assertTrue(testCourse.isCourseFull());

    int equal = 250;
    testCourse.setEnrolledStudentCount(equal);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * The test course instance and test data used for unit testing.
   */
  public static Course testCourse;
  static final String instructorName = "Griffin Newbold";
  static final String courseLocation = "417 IAB";
  static final String timeSlot = "11:40-12:55";
  static final int capacity = 250;
}

