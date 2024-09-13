package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * the tests ensures the correctness of the Course class implementation.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseUnitTests {

  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  void constructorTest() {
    Course testCourse2 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 200);
    assertEquals(200,  testCourse2.getCapacity());

    // test invalid input: negative capacity
    Course testCourse3 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", -1);
    assertEquals(0,  testCourse3.getCapacity());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    // when the capacity is reached, it should not be added
    testCourse.setEnrolledStudentCount(250);
    testCourse.enrollStudent();
    assertEquals(250, testCourse.getEnrolledStudentCount());

    // when the capacity is not reached, student should be added
    testCourse.setEnrolledStudentCount(200);
    testCourse.enrollStudent();
    assertEquals(201, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(200);
    testCourse.dropStudent();
    assertEquals(199, testCourse.getEnrolledStudentCount());

    // when the capacity is 0, it should not be dropped
    testCourse.setEnrolledStudentCount(0);
    testCourse.dropStudent();
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }


  @Test
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }


  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }


  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Adam Cannon");
    assertNotEquals("Griffin Newbold", testCourse.getInstructorName());
  }


  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("301 URIS");
    assertNotEquals("417 IAB", testCourse.getCourseLocation());
  }


  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("4:10-5:25");
    assertNotEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }


  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(200);
    assertEquals(200, testCourse.getEnrolledStudentCount());

    // test invalid input
    assertThrows(IllegalArgumentException.class, () -> testCourse.setEnrolledStudentCount(-1));
  }


  @Test
  public void isCourseFullTest() {
    // current student count is 200, expect it to be not full.
    assertEquals(false, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

