package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class test constructing course object by providing necessary parameters,
 * After creating the object, it will be compared with the manually written course
 * object. If information match, there will be no error.
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
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  /*student input
  *some test method for Course.java
  */
  @Test
  public void enrollStudentTest() {
    int cur = testCourse.getenrolledStudentCount()+1;
    if (!testCourse.enrollStudent()) {
      assertEquals(cur, testCourse.getenrolledStudentCount());
    };
  }

  @Test
  public void dropStudentTest() {
    int cur = testCourse.getenrolledStudentCount()-1;
    if (!testCourse.dropStudent()) {
      assertEquals(cur, testCourse.getenrolledStudentCount());
    };
  }

  @Test
  public void isCourseFullTest() {
    int enrollstudent = testCourse.getenrolledStudentCount();
    assertEquals(500>enrollstudent, testCourse.isCourseFull());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    int pre_enroll = testCourse.getenrolledStudentCount();
    testCourse.setEnrolledStudentCount(100);
    int expectedCount = 100;
    assertEquals(expectedCount, testCourse.getenrolledStudentCount());
    testCourse.setEnrolledStudentCount(pre_enroll);
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
  public void reassignInstructorTest() {
    String pre_instructor = testCourse.getInstructorName();
    testCourse.reassignInstructor("Jae Woo");
    assertEquals("Jae Woo", testCourse.getInstructorName());
    testCourse.reassignInstructor(pre_instructor);
  }

  @Test
  public void reassignLocationTest() {
    String pre_location = testCourse.getCourseLocation();
    testCourse.reassignLocation("IAB 501");
    assertEquals("IAB 501", testCourse.getCourseLocation());
    testCourse.reassignLocation(pre_location);
  }

  @Test
  public void reassignTimeTest() {
    String pre_time = testCourse.getCourseTimeSlot();
    testCourse.reassignTime("10:10-11:35");
    assertEquals("10:10-11:35", testCourse.getCourseTimeSlot());
    testCourse.reassignTime(pre_time);
  }

  /** The test course instance used for testing. */

  public static Course testCourse;
}
