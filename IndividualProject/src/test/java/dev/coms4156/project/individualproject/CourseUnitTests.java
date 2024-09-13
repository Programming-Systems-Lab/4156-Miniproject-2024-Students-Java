package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test cases for Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 2);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  // Check if Course information is being created correctly with constructor
  @Test
  public void courseClassTest() {
    String instructorName = "Griffin Newbold";
    String courseLocation = "417 IAB";
    String courseTimeSlot = "11:40-12:55";
    int enrollmentCapacity = 250;

    testCourse = new Course(instructorName, courseLocation, courseTimeSlot, enrollmentCapacity);

    assertEquals(instructorName, testCourse.getInstructorName());
    assertEquals(courseLocation, testCourse.getCourseLocation());
    assertEquals(courseTimeSlot, testCourse.getCourseTimeSlot());
    assertEquals(false, testCourse.isCourseFull());
  }

  // Testing enrollStudent and when enrolledStudentCount > count
  @Test
  public void enrollStudentTest() {
    String instructorName = "Griffin Newbold";
    String courseLocation = "417 IAB";
    String courseTimeSlot = "11:40-12:55";
    int enrollmentCapacity = 2;

    testCourse = new Course(instructorName, courseLocation, courseTimeSlot, enrollmentCapacity);

    assertEquals(true, testCourse.enrollStudent());

    assertEquals(true, testCourse.enrollStudent());

    assertEquals(true, testCourse.isCourseFull());
  }

  // Testing when enrolledStudentCount == 0
  @Test
  public void enrollStudentWhenCount0() {
    String instructorName = "Griffin Newbold";
    String courseLocation = "417 IAB";
    String courseTimeSlot = "11:40-12:55";
    int enrollmentCapacity = 2;

    testCourse = new Course(instructorName, courseLocation, courseTimeSlot, enrollmentCapacity);

    assertEquals(false, testCourse.dropStudent());
  }

  // Testing reassign functionality
  @Test
  public void reassignTest() {
    String instructorName = "Griffin Newbold";
    String courseLocation = "417 IAB";
    String courseTimeSlot = "11:40-12:55";
    int enrollmentCapacity = 2;

    testCourse = new Course(instructorName, courseLocation, courseTimeSlot, enrollmentCapacity);

    instructorName = "John Smith";
    courseLocation = "Remote";
    courseTimeSlot = "12:00-1:00";

    testCourse.reassignInstructor(instructorName);
    testCourse.reassignLocation(courseLocation);
    testCourse.reassignTime(courseTimeSlot);
    testCourse.setEnrolledStudentCount(5);

    assertEquals(instructorName, testCourse.getInstructorName());
    assertEquals(courseLocation, testCourse.getCourseLocation());
    assertEquals(courseTimeSlot, testCourse.getCourseTimeSlot());
    assertEquals(true, testCourse.isCourseFull());
    assertEquals(false, testCourse.enrollStudent());
    assertEquals(true, testCourse.dropStudent());

  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

