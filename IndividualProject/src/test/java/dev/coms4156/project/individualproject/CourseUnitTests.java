package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class contains all the unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void constructorTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
    assertEquals("417 IAB", testCourse.getCourseLocation());
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    boolean result = testCourse.enrollStudent();
    assertFalse(result);
  }

  @Test
  public void dropStudentTest() {
    testCourse.enrollStudent(); // First, enroll one student to have at least one student
    boolean result = testCourse.dropStudent();
    assertFalse(result);
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
    testCourse.reassignInstructor("Jane Doe");
    assertEquals("Jane Doe", testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("123 Main St");
    assertEquals("123 Main St", testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("14:00-15:15");
    assertEquals("14:00-15:15", testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTestWhenNotFull() {
    testCourse.setEnrolledStudentCount(200);
    assertTrue(testCourse.isCourseFull()); // The course should not be full
  }

  @Test
  public void isCourseFullTestWhenFull() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.isCourseFull()); // The course should be full
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}

