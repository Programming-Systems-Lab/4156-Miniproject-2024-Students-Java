package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Test class for {@link Course}. This class contains all unit tests that verify the functionality
 * of the Course class through various test cases designed to confirm both positive and expected
 * failure behaviors.
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
    testCourse.setEnrolledStudentCount(0);
    boolean result = testCourse.enrollStudent();
    assertTrue(result, "Student should be enrolled successfully");
    assertEquals(1, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrollStudentAtCapacityTest() {
    Course fullCourse = new Course("Instructor", "Location", "Time", 2);
    fullCourse.setEnrolledStudentCount(2);
    assertFalse(fullCourse.enrollStudent(), "Should not enroll when at capacity");
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    boolean result = testCourse.dropStudent();
    assertTrue(result, "Student should be dropped successfully");
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentWhenNoneEnrolledTest() {
    Course emptyCourse = new Course("Instructor", "Location", "Time", 1);
    emptyCourse.setEnrolledStudentCount(0);
    assertFalse(emptyCourse.dropStudent(), "Should not drop when no students are enrolled");
  }

  @Test
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName(), "Instructor should be updated");
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation(), "Location should be updated");
  }

  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot(), "Time should be updated");
  }


  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("New Instructor");
    assertEquals("New Instructor", testCourse.getInstructorName(), "Instructor should be updated");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("New Location");
    assertEquals("New Location", testCourse.getCourseLocation(), "Location should be updated");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("New Time");
    assertEquals("New Time", testCourse.getCourseTimeSlot(), "Time should be updated");
  }


  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}


