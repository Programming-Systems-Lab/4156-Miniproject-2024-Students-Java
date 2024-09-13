package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test cases to check methods in the Course.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Sets up a test Course object before each test.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Adam Cannon", "417 IAB",
                            "11:40-12:55", 400);
  }

  /**
   * Tests Course() constructor.
   */
  @Test
  public void constructorTest() {
    String expectedInstructorName = "Adam Cannon";
    assertEquals(expectedInstructorName, testCourse.getInstructorName());

    String expectedCourseLocation = "417 IAB";
    assertEquals(expectedCourseLocation, testCourse.getCourseLocation());

    String expectedCourseTimeSlot = "11:40-12:55";
    assertEquals(expectedCourseTimeSlot, testCourse.getCourseTimeSlot());

    int expectedEnrollmentCapacity = 400;
    assertEquals(expectedEnrollmentCapacity, testCourse.getEnrollmentCapacity());

    int expectedEnrolledStudentCount = 0;
    assertEquals(expectedEnrolledStudentCount, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests enrollStudent() method when there is space available.
   */
  @Test
  public void enrollStudentTestWhenSpaceAvailable() {
    testCourse.setEnrolledStudentCount(249);

    assertTrue(testCourse.enrollStudent());
    assertEquals(250, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests enrollStudent() method when there is no space available.
   */
  @Test
  public void enrollStudentTestWhenSpaceUnavailable() {
    testCourse.setEnrolledStudentCount(400);

    assertFalse(testCourse.enrollStudent());
    assertEquals(400, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests dropStudent() method when there are students enrolled in the course.
   */
  @Test
  public void dropStudentTestWhenStudentsEnrolled() {
    testCourse.setEnrolledStudentCount(249);

    assertTrue(testCourse.dropStudent());
    assertEquals(248, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests dropStudent() method when there is no student in the course.
   */
  @Test
  public void dropStudentTestWhenNoStudentsEnrolled() {
    assertEquals(0, testCourse.getEnrolledStudentCount());
    assertFalse(testCourse.dropStudent());
  }

  /**
   * Tests getCourseLocation() method.
   */
  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  /**
   * Tests getInstructorName() method.
   */
  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Adam Cannon";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  /**
   * Tests getCourseTimeSlot() method.
   */
  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  /**
   * Tests getEnrollmentCapacity() method.
   */
  @Test
  public void getEnrollmentCapacityTest() {
    int expectedEnrollmentCapacity = 400;
    assertEquals(expectedEnrollmentCapacity, testCourse.getEnrollmentCapacity());
  }

  /**
   * Tests getEnrolledStudentCount() method.
   */
  @Test
  public void getEnrolledStudentCountTest() {
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests toString() method.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * Tests reassignInstructor() method.
   */
  @Test
  public void reassignInstructorTest() {
    String newInstructorName = "Brian Borowski";
    testCourse.reassignInstructor(newInstructorName);
    assertEquals(newInstructorName, testCourse.getInstructorName());
  }

  /**
   * Tests reassignLocation() method.
   */
  @Test
  public void reassignLocationTest() {
    String newCourseLocation = "301 URIS";
    testCourse.reassignLocation(newCourseLocation);
    assertEquals(newCourseLocation, testCourse.getCourseLocation());
  }

  /**
   * Tests reassignTime() method.
   */
  @Test
  public void reassignTimeTest() {
    String newCourseTimeSlot = "4:10-5:25";
    testCourse.reassignTime(newCourseTimeSlot);
    assertEquals(newCourseTimeSlot, testCourse.getCourseTimeSlot());
  }

  /**
   * Tests setEnrolledStudentCount() method when neither input count < 0
   * nor input count > enrollmentCapacity.
   */
  @Test
  public void setEnrolledStudentCountTestWhenInputValid() {
    int validCount = 270;
    testCourse.setEnrolledStudentCount(validCount);
    assertEquals(validCount, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests setEnrolledStudentCount() method when input count is negative.
   */
  @Test
  public void setEnrolledStudentCountTestWhenInputNegative() {
    int invalidCount = -270;
    testCourse.setEnrolledStudentCount(invalidCount);
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests setEnrolledStudentCount() method when input count > enrollmentCapacity.
   */
  @Test
  public void setEnrolledStudentCountTestWhenInputExceedCapacity() {
    int invalidCount = 450;
    testCourse.setEnrolledStudentCount(invalidCount);
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  /**
   * Tests isCourseFull() method when enrolledStudentCount == enrollmentCapacity.
   */
  @Test
  public void isCourseFullTestWhenFull() {
    int newEnrolledStudentCount = 400;
    testCourse.setEnrolledStudentCount(newEnrolledStudentCount);
    assertTrue(testCourse.isCourseFull());
  }

  /**
   * Tests isCourseFull() method when course is not full.
   */
  @Test
  public void isCourseFullTestWhenNotFull() {
    assertFalse(testCourse.isCourseFull());
  }

  /** The Course instance used for testing. */
  private Course testCourse;
}

