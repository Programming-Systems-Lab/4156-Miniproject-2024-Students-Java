package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for methods of Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {
  /**
   * Initialize some course objects used for unit tests.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    // adding a capacity > enrolled student count
    testCourseTrueCapacity = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 600);
    testCourseSameCapacity = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    
    testCourseEnrolledStudent = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourseNotEnrolledStudent = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);

    testCourseNewInstructor = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourseNewLocation = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourseNewTime = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourseNewStudentCount = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void isCourseFullFalseTest() {
    assertFalse(testCourseTrueCapacity.isCourseFull());
  }

  @Test
  public void isCourseFullFalseTest2() {
    assertFalse(testCourseSameCapacity.isCourseFull());
  }

  @Test
  public void isCourseFullTrueTest() {
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void dropStudentFalseTest() {
    testCourseNotEnrolledStudent.setEnrolledStudentCount(0);
    assertFalse(testCourseNotEnrolledStudent.dropStudent());
  }

  @Test
  public void dropStudentTrueTest() {
    assertTrue(testCourseEnrolledStudent.dropStudent());
  }

  @Test
  public void enrollStudentFalseTest() {
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentTrueTest() {
    assertTrue(testCourseTrueCapacity.enrollStudent());
  }

  @Test
  public void getCourseLocationTest() {
    String expectedLocation = "417 IAB";
    assertEquals(expectedLocation, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedInstructor = "Griffin Newbold";
    assertEquals(expectedInstructor, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedTimeSlot = "11:40-12:55";
    assertEquals(expectedTimeSlot, testCourse.getCourseTimeSlot());
  }

  @Test
  public void getStudentCountTest() {
    int expectedStudentCount = 500;
    assertEquals(expectedStudentCount, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void getEnrollmentCapacityTest() {
    assertEquals(250, testCourse.getEnrollmentCapacity());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignInstructorTest() {
    String newInstructorName = "Gail Kaiser";
    testCourseNewInstructor.reassignInstructor(newInstructorName);
    assertEquals(newInstructorName, testCourseNewInstructor.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String newLocation = "628 IAB";
    testCourseNewLocation.reassignLocation(newLocation);
    assertEquals(newLocation, testCourseNewLocation.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String newTime = "12:40-13:55";
    testCourseNewTime.reassignTime(newTime);
    assertEquals(newTime, testCourseNewTime.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCount() {
    int newCount = 300;
    testCourseNewStudentCount.setEnrolledStudentCount(newCount);
    assertEquals(newCount, testCourseNewStudentCount.getEnrolledStudentCount());
  }

  @Test
  public void equalsFalse1Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsFalse2Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = null;
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsFalse3Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Object obj = new Object();
    assertFalse(testCourseDiff1.equals(obj));
  }

  @Test
  public void equalsFalse4Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Brian Borowski", "417 IAB", "11:40-12:55", 250);
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsFalse5Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Griffin Newbold", "417 IAB", "13:40-15:55", 250);
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsFalse6Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsFalse7Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourseDiff2.setEnrolledStudentCount(100);
    assertFalse(testCourseDiff1.equals(testCourseDiff2));
  }

  @Test
  public void equalsTrue1Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    assertTrue(testCourseDiff1.equals(testCourseDiff1));
  }

  @Test
  public void equalsTrue2Test() {
    Course testCourseDiff1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourseDiff2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    assertTrue(testCourseDiff1.equals(testCourseDiff2));
  }
  

  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testCourseTrueCapacity;
  public static Course testCourseSameCapacity;
  public static Course testCourseEnrolledStudent;
  public static Course testCourseNotEnrolledStudent;
  public static Course testCourseNewInstructor;
  public static Course testCourseNewLocation;
  public static Course testCourseNewTime;
  public static Course testCourseNewStudentCount;
}