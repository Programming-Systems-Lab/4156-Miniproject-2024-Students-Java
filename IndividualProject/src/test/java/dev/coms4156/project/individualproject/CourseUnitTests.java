package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is just testing if we can create a course object and return expected results from each
 * method.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {
  
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }
  
  
  @Test
  public void toStringTest() {
    String expectedResult =
        "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }
  
  /**
   * The test course has a capacity of 250 so students can be enrolled with a 200 student head
   * count in the course set by the test.
   */
  @Test
  public void enrollStudentOpenTest() {
    testCourse.setEnrolledStudentCount(200);
    assertEquals(true, testCourse.enrollStudent());
  }
  
  /**
   * The test course has a capacity of 250 so students cannot be enrolled with a 500 student head
   * count in the course set by the test.
   */
  @Test
  public void enrollStudentCloseTest() {
    testCourse.setEnrolledStudentCount(500);
    assertEquals(false, testCourse.enrollStudent());
  }
  
  /**
   * Testing to see if we can drop students from our course.
   */
  @Test
  public void dropStudentTest() {
    assertEquals(true, testCourse.dropStudent());
  }
  
  
  /**
   * Checking if we can grab the glorious instructor's name from our instantiated course.
   */
  @Test
  public void getInstructorTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }
  
  /**
   * Checking if we can grab the glorious location of this course.
   */
  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }
  
  /**
   * Checking if we can grab the glorious time of this course.
   */
  @Test
  public void getCourseTimeTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }
  
  /**
   * Checking if we can change the instructor of this massive class.
   */
  @Test
  public void changeInstructorTest() {
    String temp = testCourse.getInstructorName();
    testCourse.reassignInstructor("DFerg");
    String expectedResult = "DFerg";
    assertEquals(expectedResult, testCourse.getInstructorName());
    testCourse.reassignInstructor(temp);
  }
  
  /**
   * Checking if we can change the location of this sick class.
   */
  @Test
  public void changeLocationTest() {
    String temp = testCourse.getCourseLocation();
    testCourse.reassignLocation("Columbia's Wrestling Room");
    String expectedResult = "Columbia's Wrestling Room";
    assertEquals(expectedResult, testCourse.getCourseLocation());
    testCourse.reassignLocation(temp);
  }
  
  /**
   * Checking if we can change the time of this interesting class.
   */
  @Test
  public void changeTimeSlotTest() {
    String temp = testCourse.getCourseTimeSlot();
    testCourse.reassignTime("To Infinity and Beyond");
    String expectedResult = "To Infinity and Beyond";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
    testCourse.reassignTime(temp);
  }
  
  /**
   * The test course instance used for testing.
   */
  public static Course testCourse;
}

