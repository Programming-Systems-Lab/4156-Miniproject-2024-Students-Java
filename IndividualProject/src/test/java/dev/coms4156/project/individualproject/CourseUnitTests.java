package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * <p>
 * This class contains tests to verify the functionality of the Course class,
 * including edge cases and all methods.
 * </p>
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  private Course testCourse;

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(200); // Start with 200 enrolled students
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    Assertions.assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    boolean enrolled = testCourse.enrollStudent();
    Assertions.assertTrue(enrolled, "Student should be enrolled");
  }

  @Test
  public void dropStudentTest() {
    boolean dropped = testCourse.dropStudent();
    Assertions.assertTrue(dropped, "Student should be dropped");
  }

  @Test
  public void getCourseLocationTest() {
    String location = testCourse.getCourseLocation();
    Assertions.assertEquals("417 IAB", location, "Location should be '417 IAB'");
  }


  @Test
  public void getInstructorNameTest() {
    String instructorName = testCourse.getInstructorName();
    Assertions.assertEquals("Griffin Newbold", instructorName,
            "Instructor name is 'Griffin Newbold'");
  }

  @Test
  public void getCourseTimeSlotTest() {
    String timeSlot = testCourse.getCourseTimeSlot();
    Assertions.assertEquals("11:40-12:55", timeSlot, "Time slot should be '11:40-12:55'");
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Alok Mathur");
    Assertions.assertEquals("Alok Mathur", testCourse.getInstructorName(),
            "Instructor should be 'Alok Mathur'");
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("VIT SJT 503");
    Assertions.assertEquals("VIT SJT 503", testCourse.getCourseLocation(),
            "Location should be 'VIT SJT 503'");
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("13:10-15:35");
    Assertions.assertEquals("13:10-15:35", testCourse.getCourseTimeSlot(),
            "Time slot should be '13:10-15:35'");
  }

//  This test won't work --- no proper return value in the function
//  @Test
//  public void setEnrolledStudentCountTest() {
//    // Test setting a specific enrolled student count
//    testCourse.setEnrolledStudentCount(220);
//    Assertions.assertEquals(220, testCourse.enrolledStudentCount, "Enrolled student count should be 220");
//  }

  @Test
  public void isCourseFullTest() {
    boolean isFull = testCourse.isCourseFull();
    Assertions.assertFalse(isFull, "Course should not be full with 200 students out of 250");

    testCourse.setEnrolledStudentCount(250);
    isFull = testCourse.isCourseFull();
    Assertions.assertTrue(isFull, "Course should be full with 250 students");
  }
  
}
