package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
  /**
   * Sets up testCourse for testing purposes.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void getEnrolledStudentCountTest() {
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrollStudentTest() {
    testCourse.enrollStudent();
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrollFailTest() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 0);
    testCourse.enrollStudent();
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest() {
    testCourse.enrollStudent();
    testCourse.dropStudent();
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentFailTest() {
    assertFalse(testCourse.dropStudent());
  }


  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(10);
    assertEquals(10, testCourse.getEnrolledStudentCount());
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
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Suwei Ma");
    assertEquals("Suwei Ma", testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("418 IAB");
    assertEquals("418 IAB", testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("11:50-1:05");
    assertEquals("11:50-1:05", testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void courseNotFullTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void equalsTest() {
    Course testCourseNew = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    assertEquals(testCourse, testCourseNew);
    testCourseNew.enrollStudent();
    assertNotEquals(testCourse, testCourseNew);
    testCourseNew = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 251);
    assertNotEquals(testCourse, testCourseNew);
    testCourseNew = new Course("Griffin", "417 IAB", "11:40-12:55", 250);
    assertNotEquals(testCourse, testCourseNew);
    int x = 10;
    assertNotEquals(testCourse, x);
  }

  @Test
  public void hashCodeTest() {
    Course testCourseNew = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    assertEquals(testCourse.hashCode(), testCourseNew.hashCode());
  }

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

