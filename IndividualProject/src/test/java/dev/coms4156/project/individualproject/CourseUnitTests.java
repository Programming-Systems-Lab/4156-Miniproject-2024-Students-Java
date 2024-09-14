package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class implements Unit Tests for the {@link Course} class.
 *
 * <p>This includes testing the enrollStudent and dropStudent methods.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  private Course testCourse;

  /**
   * Retrieves course information from the data.txt
   * In this instance, the {@code setupCourseForTesting()} method retrieves
   * the course "1004" from the COMS department to use as an example in
   * testing.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    myFileDatabase = new MyFileDatabase(0, "./data.txt"); // Adjust the path if needed
    originalTestCourse = myFileDatabase.getDepartmentMapping()
      .get("COMS").getCourseSelection().get("1004");
  }

  /**
   * Generates a deep copy of the originalTestCourse object for
   * debugging purposes. Enables changes to verify class methods.
   */
  @BeforeEach
  public void setupTestCourseForEachTest() {
    testCourse = originalTestCourse.cloneCourse();
  }


  @Test
  public void toStringTest() {
    testCourse = myFileDatabase.getDepartmentMapping().get("COMS").getCourseSelection().get("1004");
    String expectedResult = "\nInstructor: Adam Cannon; Location: "
        + "417 IAB; Time: 11:40-12:55; Capacity: 400";
    assertEquals(expectedResult, testCourse.toString());
  }

  /**
   * This function tests the {@code enrollStudentSuccess()} method.
   *
   * <p>If the course student enrollment is less than the capacity of the class,
   * this method should return true.
   */
  @Test
  public void enrollStudentSuccess() {
    assertTrue(testCourse.enrollStudent());
  }

  /**
   * This function tests the {@code enrollStudentSuccess()} method.
   *
   * <p>If the course student enrollment is greater than the capacity of the class,
   * this method should return false.
   */
  @Test
  public void enrollStudentFailure() {

    testCourse.setEnrolledCount(testCourse.getCourseCapacity());
    assertFalse(testCourse.enrollStudent());
  }

  /**
   * This function tests the {@code dropStudent()} method.
   *
   * <p>If the course student enrollment greater than 0, this method should return true.
   */
  @Test
  public void dropStudentSuccess() {
    testCourse.setEnrolledCount(5);
    assertTrue(testCourse.dropStudent());
  }

  /**
   * This function tests the {@code dropStudent()} method.
   *
   * <p>If the course student enrollment 0 (or somehow lower), this method should return false.
   */
  @Test
  public void dropStudentFailure() {
    testCourse.setEnrolledCount(0);
    assertFalse(testCourse.dropStudent());
  }

  /**
   * This function tests the {@code getCourseLocation()} method.
   */
  @Test
  public void testGetCourseLocation() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  /**
   * This function tests the {@code getInstructorName()} method.
   */
  @Test
  public void testGetInstructorName() {
    String expectedResult = "Adam Cannon";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  /**
   * This function tests the {@code getCourseTimeSlot()} method.
   */
  @Test
  public void testGetCourseTimeSlot() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  /**
   * This function tests the {@code reassignInstructor()} method.
   */
  @Test
  public void testReassignInstructor() {
    String newName = "Nicholas Ching";
    testCourse.reassignInstructor(newName);
    assertEquals(newName, testCourse.getInstructorName());
  }

  /**
   * This function tests the {@code reassignLocation()} method.
   */
  @Test
  public void testReassignLocation() {
    String newLocation = "CSB 818";
    testCourse.reassignLocation(newLocation);
    assertEquals(newLocation, testCourse.getCourseLocation());
  }

  /**
   * This function tests the {@code reassignTime()} method.
   */
  @Test
  public void testReassignTime() {
    String newTime = "14:40-15:55";
    testCourse.reassignTime(newTime);
    assertEquals(newTime, testCourse.getCourseTimeSlot());
  }

  /**
   * This function tests the {@code setEnrolledStudentCount()} method.
   */
  @Test
  public void testSetEnrolledStudentCount() {
    int newEnrollmentCount = 350;
    testCourse.setEnrolledCount(newEnrollmentCount);
    assertEquals(newEnrollmentCount, testCourse.getEnrolledCount());
  }

  /**
   * This function tests the {@code isCourseFull()} method.
   *
   * <p>If the course is full, this method should return true
   */
  @Test
  public void testIsCourseFullSuccess() {
    int amountAboveCapacity = testCourse.getCourseCapacity();
    testCourse.setEnrolledCount(amountAboveCapacity);
    assertTrue(testCourse.isCourseFull());
  }

  /**
   * This function tests the {@code isCourseFull()} method.
   *
   * <p>If the course is not full, this method should return false
   */
  @Test
  public void testIsCourseFullFailure() {
    testCourse.setEnrolledCount(testCourse.getCourseCapacity() - 1);
    assertFalse(testCourse.isCourseFull());
  }



  /** The test course instance used for testing. */
  private static MyFileDatabase myFileDatabase;
  private static Course originalTestCourse;

}

