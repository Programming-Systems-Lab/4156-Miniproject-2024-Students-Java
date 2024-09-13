package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** 
 * This test class verifies the functionality of the {@link Course} class 
 * by testing its methods.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", defaultStudentCap);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    Assertions.assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void getInstructorTest() {
    String expectedInstructor = "Griffin Newbold";
    Assertions.assertEquals(expectedInstructor, testCourse.getInstructorName());
  }

  @Test
  public void getLocationTest() {
    String expectedLocation = "417 IAB";
    Assertions.assertEquals(expectedLocation, testCourse.getCourseLocation());
  }

  @Test
  public void getTimeTest() {
    String expectedTime = "11:40-12:55";
    Assertions.assertEquals(expectedTime, testCourse.getCourseTimeSlot());
  }

  @Test
  public void defaultCourseFullTest() {
    Assertions.assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void enrollStudentsTest() {
    for(int i = 0; i < 10; i++){
      Assertions.assertTrue(testCourse.enrollStudent());
    }
    Assertions.assertTrue(testCourse.isCourseFull());
    Assertions.assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentsTest() {
    Assertions.assertFalse(testCourse.dropStudent());
    testCourse.enrollStudent();
    Assertions.assertTrue(testCourse.dropStudent());
  }


  @Test
  public void reassignFuncTest() {
    String expectedInstructor = "Keren Bergmant";
    String expectedLocation = "903 SSWt";
    String expectedTime = "7:00-9:30t";
    testCourse.reassignInstructor(expectedInstructor);
    testCourse.reassignLocation(expectedLocation);
    testCourse.reassignTime(expectedTime);
    Assertions.assertEquals(expectedInstructor, testCourse.getInstructorName());
    Assertions.assertEquals(expectedLocation, testCourse.getCourseLocation());
    Assertions.assertEquals(expectedTime, testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrollmentCountTest() {
    testCourse.setEnrolledStudentCount(0);
    Assertions.assertEquals(false, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(500);
    Assertions.assertEquals(true, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(-1);
    Assertions.assertEquals(true, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
  public static int defaultStudentCap = 10;
}



  

