package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(0);
    testCourse.enrollStudent();
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    testCourse.dropStudent();
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignLocationTest() {
    String expectedResult = "207 Math";
    testCourse.reassignLocation(expectedResult);
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Adam Cannon";
    testCourse.reassignInstructor(expectedResult);
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "1:10-3:40";
    testCourse.reassignTime(expectedResult);
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    int expectedResult = 80;
    testCourse.setEnrolledStudentCount(expectedResult);
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void isCourseFull_whenNotFull() {
    testCourse.setEnrolledStudentCount(249);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFull_whenFull() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFull_whenOverEnrolled() {
    testCourse.setEnrolledStudentCount(251);
    assertTrue(testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

