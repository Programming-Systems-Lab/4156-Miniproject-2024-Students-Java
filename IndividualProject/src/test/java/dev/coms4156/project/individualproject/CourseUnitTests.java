package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class is a test for Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  public static Course testCourse;

  /**
   * This function create a course before
   * each test method run in the class.
   */
  @BeforeEach
  public void setup() {
    testCourse = new Course("Adam Cannon",
        "417 IAB",
        "11:40-12:55",
        400);
    testCourse.setEnrolledStudentCount(240);
  }

  @Test
  public void enrollStudentTest() {
    int expectedResult = 241;
    testCourse.enrollStudent();
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest() {
    int expectedResult = 239;
    testCourse.dropStudent();
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Adam Cannon";
    assertEquals(expectedResult, testCourse.getInstructorName());
}

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void getEnrollmentCapacityTest() {
    int expectedResult = 400;
    assertEquals(expectedResult, testCourse.getEnrollmentCapacity());
  }

  @Test
  public void getEnrolledStudentCountTest() {
    int expectedResult = 240;
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Jae Lee";
    testCourse.reassignInstructor(expectedResult);
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String expectedResult = "301 URIS";
    testCourse.reassignLocation(expectedResult);
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "4:10-5:25";
    testCourse.reassignTime(expectedResult);
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    int expectedResult = 380;
    testCourse.setEnrolledStudentCount(expectedResult);
    assertEquals(expectedResult, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(400);
    assertTrue(testCourse.isCourseFull());
  }
}

