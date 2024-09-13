package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    // Enroll students and ensure the enrollment succeeds
    for (int i = 0; i < 5; i++) {
      assertTrue(testCourse.enrollStudent(), "Enrollment should succeed for student " + (i + 1));
    }

    // Now try to fill up the course
    for (int i = 5; i < 250; i++) {
      testCourse.enrollStudent();
    }

    // Verify the course is now full and further enrollment is prevented
    assertTrue(testCourse.isCourseFull(), "Course should be full when capacity is reached.");
    assertFalse(testCourse.enrollStudent(), "Enrollment should fail once course is full.");
  }

  @Test
  public void dropStudentTest() {
    // First enroll a few students to test the drop functionality
    for (int i = 0; i < 5; i++) {
      testCourse.enrollStudent();
    }

    // Drop students and check the drop succeeds
    for (int i = 0; i < 5; i++) {
      assertTrue(testCourse.dropStudent(), "Dropping should succeed for student " + (i + 1));
    }

    // Try to drop more than available students (should fail after dropping all)
    assertFalse(testCourse.dropStudent(), "Dropping should fail when no students are enrolled.");
  }

  @Test
  public void isCourseFullTest() {
    // Enroll students until the course is full
    testCourse.setEnrolledStudentCount(250); // Manually set the count to full capacity
    assertTrue(testCourse.isCourseFull(), "Course should be full when enrollment reaches capacity.");

    // Test that enrollment fails when full
    assertFalse(testCourse.enrollStudent(), "Enrollment should fail when the course is full.");
  }

  @Test
  public void isCourseNotFullTest() {
    // Reset enrollment count to less than capacity
    testCourse.setEnrolledStudentCount(200);
    assertFalse(testCourse.isCourseFull(), "Course should not be full when under capacity.");
  }

  @Test
  public void reassignInstructorTest() {
    // Reassign the instructor and verify the update
    testCourse.reassignInstructor("New Instructor");
    assertEquals("New Instructor", testCourse.getInstructorName(), "Instructor should be updated.");
  }

  @Test
  public void reassignLocationTest() {
    // Reassign the location and verify the update
    testCourse.reassignLocation("New Location");
    assertEquals("New Location", testCourse.getCourseLocation(), "Location should be updated.");
  }

  @Test
  public void reassignTimeTest() {
    // Reassign the course time slot and verify the update
    testCourse.reassignTime("1:10-2:25");
    assertEquals("1:10-2:25", testCourse.getCourseTimeSlot(), "Time slot should be updated.");
  }

  @Test
  public void setEnrolledStudentCountTest() {
    // Test manually setting the enrolled student count
    testCourse.setEnrolledStudentCount(100);
    for (int i = 0; i < 150; i++) {
      assertTrue(testCourse.enrollStudent(), "Enrollment should succeed until course is full.");
    }

    // Test when we reach the capacity limit
    assertFalse(testCourse.enrollStudent(), "Enrollment should fail once course is full.");
  }

  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

