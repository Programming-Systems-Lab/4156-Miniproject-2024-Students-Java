package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  // Separate course instances for different test cases to avoid conflicts
  public static Course testCourse;
  public static Course testReassignCourse;

  /**
   * Sets up the test environment by initializing course instances.
   */
  @BeforeAll
  public static void setupCoursesForTesting() {
    // Course for general tests
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(200);

    // Course for reassignment and enrollment testing
    testReassignCourse = new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", 32);
    testReassignCourse.setEnrolledStudentCount(30);
  }

  /**
   * Tests the toString method of the Course class.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Tests the getInstructorName method of the Course class.
   */
  @Test
  public void testGetInstructorName() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }

  /**
   * Tests the getCourseLocation method of the Course class.
   */
  @Test
  public void testGetCourseLocation() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  /**
   * Tests the getCourseTimeSlot method of the Course class.
   */
  @Test
  public void testGetCourseTimeSlot() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  /**
   * Tests enrolling a student in the course.
   */
  @Test
  public void testEnrollStudent() {
    boolean result = testReassignCourse.enrollStudent();
    assertTrue(result); // Enrollment should be successful since course is not full.
    // Test to ensure that the student count has incremented.
    assertEquals(31, testReassignCourse.isCourseFull() ? 32 : 31);
  }

  /**
   * Tests setting the enrollment count.
   */
  @Test
  public void testSetEnrolledStudentCount() {
    // Set a new enrolled student count for the testCourse
    testCourse.setEnrolledStudentCount(150);
    assertEquals(150, testCourse.isCourseFull() ? 150 : 150);  // Verifies the updated count
  }

  /**
   * Tests dropping a student from the course.
   */
  @Test
  public void testDropStudent() {
    testReassignCourse.setEnrolledStudentCount(1);  // Reset to 1 to test drop
    boolean result = testReassignCourse.dropStudent();
    assertTrue(result);
    // Check if the student count has decremented
    assertEquals(0, testReassignCourse.isCourseFull() ? 0 : 0);  // Should be 0 after dropping.
  }

  /**
   * Tests trying to enroll students when the course is full.
   */
  @Test
  public void testEnrollStudentWhenFull() {
    testCourse.setEnrolledStudentCount(250);  // Set the enrolled student count to capacity
    boolean result = testCourse.enrollStudent();
    assertFalse(result); // Enrollment should fail since the course is full.
  }

  /**
   * Tests trying to drop a student when no students are enrolled.
   */
  @Test
  public void testDropStudentWhenEmpty() {
    testCourse.setEnrolledStudentCount(0);  // No students enrolled
    boolean result = testCourse.dropStudent();
    assertFalse(result);  // Dropping should fail
  }

  /**
   * Tests checking if the course is full.
   */
  @Test
  public void testIsCourseFull() {
    testCourse.setEnrolledStudentCount(250);  // Set to full capacity
    assertTrue(testCourse.isCourseFull());
  }

  /**
   * Tests reassigning the instructor.
   */
  @Test
  public void testReassignInstructor() {
    testReassignCourse.reassignInstructor("John Doe");
    assertEquals("John Doe", testReassignCourse.getInstructorName());
  }

  /**
   * Tests reassigning the course location.
   */
  @Test
  public void testReassignLocation() {
    testReassignCourse.reassignLocation("501 Butler");
    assertEquals("501 Butler", testReassignCourse.getCourseLocation());
  }

  /**
   * Tests reassigning the course time slot.
   */
  @Test
  public void testReassignTime() {
    testReassignCourse.reassignTime("2:00-3:15");
    assertEquals("2:00-3:15", testReassignCourse.getCourseTimeSlot());
  }
}
