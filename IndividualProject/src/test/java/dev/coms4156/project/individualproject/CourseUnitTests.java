package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
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
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Gail Kaiser";
    testCourse2.reassignInstructor("Gail Kaiser");
    assertEquals(expectedResult, testCourse2.getInstructorName());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "4:10-6:40";
    testCourse2.reassignTime("4:10-6:40");
    assertEquals(expectedResult, testCourse2.getCourseTimeSlot());
  }

  @Test
  public void reassignLocationTest() {
    String expectedResult = "501 NWC";
    testCourse2.reassignLocation("501 NWC");
    assertEquals(expectedResult, testCourse2.getCourseLocation());
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse2.setEnrolledStudentCount(0);
    assertEquals(false, testCourse2.isCourseFull());
  }

  @Test
  public void enrollStudentTest() {
    testCourse2.setEnrolledStudentCount(0);
    assertEquals(false, testCourse.enrollStudent());
    assertEquals(true, testCourse2.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    testCourse2.setEnrolledStudentCount(0);
    assertEquals(true, testCourse.dropStudent());
    assertEquals(false, testCourse2.dropStudent());
  }


  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testCourse2;
}

