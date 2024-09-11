package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Course.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    testCourse.setEnrolledStudentCount(249);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }


  @Test
  public void enrollStudentTestWhenSpaceAvailable() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }


  @Test
  public void enrollStudentTestWhenSpaceUnavailable() {
    testCourse.setEnrolledStudentCount(400);
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }


  @Test
  public void dropStudentTestWhenStudentsEnrolled() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.dropStudent());
  }

  @Test
  public void dropStudentTestWhenNoStudentsEnrolled() {
    testCourse.setEnrolledStudentCount(0);
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.dropStudent());
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
  public void reassignInstructorTest() {
    String newInstructorName = "Brian Borowski";
    testCourse.reassignInstructor(newInstructorName);
    assertEquals(newInstructorName, testCourse.getInstructorName());
  }


  @Test
  public void reassignLocationTest() {
    String newCourseLocation = "301 URIS";
    testCourse.reassignLocation(newCourseLocation);
    assertEquals(newCourseLocation, testCourse.getCourseLocation());
  }


  @Test
  public void reassignTimeTest() {
    String newCourseTimeSlot = "4:10-5:25";
    testCourse.reassignTime(newCourseTimeSlot);
    assertEquals(newCourseTimeSlot, testCourse.getCourseTimeSlot());
  }


  @Test
  public void isCourseFullTestWhenFull() {
    int newEnrolledStudentCount = 400;
    testCourse.setEnrolledStudentCount(newEnrolledStudentCount);
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }


  @Test
  public void isCourseFullTestWhenOverCapacity() {
    int newEnrolledStudentCount = 450;
    testCourse.setEnrolledStudentCount(newEnrolledStudentCount);
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }


  @Test
  public void isCourseFullTestWhenNotFull() {
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

