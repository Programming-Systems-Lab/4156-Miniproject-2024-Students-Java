package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class contains all the test logic for the course class
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
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentTestEdge() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }


  @Test
  public void dropStudentTest() {
    assertFalse(testCourse.dropStudent());
  }


  @Test
  public void dropStudentTestEdge() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
  }


  @Test
  public void getCourseLocationTest() {
    assertEquals(testCourse.getCourseLocation(), "417 IAB");
  }


  @Test
  public void getInstructorNameTest() {
    assertEquals(testCourse.getInstructorName(), "Griffin Newbold");
  }


  @Test
  public void getCourseTimeSlotTest() {
    assertEquals(testCourse.getCourseTimeSlot(), "11:40-12:55");
  }


  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Louis Lu");
    assertEquals(testCourse.getInstructorName(), "Louis Lu");
  }


  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("833 Mudd");
    assertEquals(testCourse.getCourseLocation(), "833 Mudd");
  }


  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("Midnight");
    assertEquals(testCourse.getCourseLocation(), "Midnight");
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.isCourseFull());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
}

