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
 *
 * <p>This class contains methods to test the functionality of the Course class, 
 * including testing the toString() method.
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
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249); // Set to one less than capacity
    assertTrue(
        testCourse.enrollStudent(), 
        "Should allow enrolling a student when space is available"
    );
    
    testCourse.setEnrolledStudentCount(250); // Set to capacity
    assertFalse(testCourse.enrollStudent(), "Should not allow enrolling when course is full");
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(250); // Set to capacity
    assertTrue(
        testCourse.dropStudent(), 
        "Should allow dropping a student when enrolled"
    );

    testCourse.setEnrolledStudentCount(0); // No students enrolled
    assertFalse(
        testCourse.dropStudent(), 
        "Should not allow dropping when no students are enrolled"
    );
  }

  @Test
  public void reassignLocationTest() {
    assertEquals(
        "417 IAB", 
        testCourse.getCourseLocation(),
        "Location should be the one previously defined"
    );

    testCourse.reassignLocation("302 Uris");
    assertEquals(
        "302 Uris", 
        testCourse.getCourseLocation(), 
        "Location should update correctly"
    );
  }

  @Test
  public void reassignInstructorTest() {
    assertEquals(
        "Griffin Newbold", 
        testCourse.getInstructorName(),
        "Instructor name should be the one previously defined"
    );
    testCourse.reassignInstructor("Nicole Lin");
    assertEquals(
        "Nicole Lin", 
        testCourse.getInstructorName(), 
        "Instructor name should update correctly"
    );
  }

  @Test
  public void reassignTimeTest() {
    assertEquals(
        "11:40-12:55", 
        testCourse.getCourseTimeSlot(), 
        "Time slot should be the one previously defined"
    );

    testCourse.reassignTime("8:40-9:55");
    assertEquals(
        "8:40-9:55", 
        testCourse.getCourseTimeSlot(), 
        "Time slot should update correctly"
    );
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250); // Set to capacity
    assertTrue(
        testCourse.isCourseFull(), 
        "Course should be considered full when enrollment = capacity"
    );
    
    testCourse.setEnrolledStudentCount(100); // Set to below capacity
    assertFalse(
        testCourse.isCourseFull(), 
        "Course should not be considered full when enrollment is below capacity"
    );
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}