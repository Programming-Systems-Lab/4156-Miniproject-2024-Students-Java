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
 * Tests the functionality of the Course class, such as the toString method and course setup.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {
  /**
   * Sets up the test course instance before all tests are run.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /**
   * Tests the toString method of the Course class.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    for (int i = 0; i < 5; i++) {
      assertTrue(testCourse.enrollStudent(), "Enrollment should succeed for student " + (i + 1));
    }

    // Fill up the course
    for (int i = 5; i < 250; i++) {
      testCourse.enrollStudent();
    }

    // Verify the course is now full and further enrollment is prevented
    assertTrue(testCourse.isCourseFull(), "Course should be full when capacity is reached.");
    assertFalse(testCourse.enrollStudent(), "Enrollment should fail once course is full.");
  }

  @Test
  public void dropStudentTest() {
    for (int i = 0; i < 5; i++) {
      testCourse.enrollStudent();
    }

    for (int i = 0; i < 5; i++) {
      assertTrue(testCourse.dropStudent(), "Dropping should succeed for student " + (i + 1));
    }

    assertFalse(testCourse.dropStudent(), "Dropping should fail when no students are enrolled.");
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull(), "Course shows full when enrollment reaches capacity.");

    assertFalse(testCourse.enrollStudent(), "Enrollment should fail when the course is full.");
  }

  @Test
  public void isCourseNotFullTest() {
    testCourse.setEnrolledStudentCount(200);
    assertFalse(testCourse.isCourseFull(), "Course should not be full when under capacity.");
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("New Instructor");
    assertEquals("New Instructor", testCourse.getInstructorName(), "Instructor should be updated.");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("New Location");
    assertEquals("New Location", testCourse.getCourseLocation(), "Location should be updated.");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("1:10-2:25");
    assertEquals("1:10-2:25", testCourse.getCourseTimeSlot(), "Time slot should be updated.");
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(100);
    for (int i = 0; i < 150; i++) {
      assertTrue(testCourse.enrollStudent(), "Enrollment should succeed until course is full.");
    }

    assertFalse(testCourse.enrollStudent(), "Enrollment should fail once course is full.");
  }

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

