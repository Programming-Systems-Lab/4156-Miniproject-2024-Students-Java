package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentSuccessTest() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentAtCapacityTest() {
    boolean expectedResult = false;
    testCourse.setEnrolledStudentCount(125);
    assertEquals(expectedResult, testCourse.enrollStudent());
  }

  @Test
  public void dropStudentSuccessTest() {
    boolean expectedResult = true;
    testCourse.setEnrolledStudentCount(3);
    assertEquals(expectedResult, testCourse.dropStudent());
  }

  @Test
  public void dropStudentAtEmptyTest() {
    boolean expectedResult = false;
    testCourse.setEnrolledStudentCount(0);
    assertEquals(expectedResult, testCourse.dropStudent());
  }


  @Test
  public void getCourseLocationTest() {
    String expectedResult = "402 CHANDLER";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Tony Dear";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "1:10-3:40";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Brian Borowski");
    String expectedResult = "Brian Borowski";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }


  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("301 URIS");
    String expectedResult = "301 URIS";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("4:10-5:25");
    String expectedResult = "4:10-5:25";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void isCourseFullTrueTest() {
    testCourse.setEnrolledStudentCount(125);
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullFalseTest() {
    testCourse.setEnrolledStudentCount(50);
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

