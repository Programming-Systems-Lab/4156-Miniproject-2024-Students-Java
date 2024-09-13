package dev.coms4156.project.individualproject;

// import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  public static Course testCourse;

  // @BeforeAll
  // public static void setupCourseForTesting() {
  //   testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  // }
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  // /** The test course instance used for testing. */
  // public static Course testCourse;

  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent(), "Enrolled student successfully");
    assertEquals(250, testCourse.getEnrolledStudentCount(), "Enrollment count increases by one");
  }

  @Test
  public void enrollStudentCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent(), "Cannot enroll student when course is full");
    assertEquals(250, testCourse.getEnrolledStudentCount(), "Enrollment count remains the same");
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent(), "Dropped student successfully");
    assertEquals(0, testCourse.getEnrolledStudentCount(), "Enrollment count decreases by one");
  }

  @Test
  public void dropStudentNoStudentsTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent(), "Cannot drop student when no students are enrolled");
    assertEquals(0, testCourse.getEnrolledStudentCount(), "Enrollment count remains the same");
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals("Gail Kaiser", testCourse.getInstructorName(), "Instructor is reassigned");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("Mudd 4156");
    assertEquals("Mudd 4156", testCourse.getCourseLocation(), "Location is reassigned");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("10:10-11:25");
    assertEquals("10:10-11:25", testCourse.getCourseTimeSlot(), "Time should be reassigned");
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull(), "Course should be full when enrollment matches capacity");
  }

  @Test
  public void isCourseNotFullTest() {
    testCourse.setEnrolledStudentCount(100);
    assertFalse(testCourse.isCourseFull(), "Course should not be full when enrollment is below capacity");
  }
}

