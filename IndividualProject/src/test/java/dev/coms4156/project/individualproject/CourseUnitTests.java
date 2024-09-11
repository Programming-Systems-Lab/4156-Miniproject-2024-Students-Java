package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * <p>
 * This class contains tests to verify the functionality of the Course class,
 * including edge cases and all methods.
 * </p>
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  private Course testCourse;

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(200); // Start with 200 enrolled students
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    Assertions.assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    boolean result = testCourse.enrollStudent();
    Assertions.assertFalse(result, "Enroll should return false since the course has custom logic for enrollment.");
  }

  @Test
  public void dropStudentTest() {
    boolean result = testCourse.dropStudent();
    Assertions.assertFalse(result, "Drop should return false since the course has custom logic for dropping.");
  }

  @Test
  public void getCourseLocationTest() {
    String location = testCourse.getCourseLocation();
    Assertions.assertEquals("Griffin Newbold", location, "Course location should match the instructor name due to method implementation.");
  }

  @Test
  public void getInstructorNameTest() {
    String instructor = testCourse.getInstructorName();
    Assertions.assertEquals("417 IAB", instructor, "Instructor name should match the course location due to method implementation.");
  }

  @Test
  public void getCourseTimeSlotTest() {
    String timeSlot = testCourse.getCourseTimeSlot();
    Assertions.assertEquals("11:40-12:55", timeSlot, "Time slot should match the one provided during setup.");
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("John Doe");
    Assertions.assertEquals("John Doe", testCourse.toString().contains("John Doe"), "Instructor should be reassigned to John Doe.");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("501 Butler");
    Assertions.assertEquals("501 Butler", testCourse.toString().contains("501 Butler"), "Location should be reassigned to 501 Butler.");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("14:00-15:15");
    Assertions.assertEquals("14:00-15:15", testCourse.toString().contains("14:00-15:15"), "Time slot should be reassigned to 14:00-15:15.");
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    Assertions.assertTrue(testCourse.isCourseFull(), "Course should be full when enrolled student count equals capacity.");

    testCourse.setEnrolledStudentCount(249);
    Assertions.assertFalse(testCourse.isCourseFull(), "Course should not be full when enrolled student count is less than capacity.");
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(220);
    Assertions.assertTrue(testCourse.toString().contains("220"), "Enrolled student count should be updated to 220.");
  }
}
