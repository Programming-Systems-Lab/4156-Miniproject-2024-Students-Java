package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
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

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  @Order(0)
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  @Order(1)
  public void enrollStudentTest() {
    testCourse.enrollStudent();
    assertEquals(501, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(2)
  public void dropStudentTest() {
    testCourse.dropStudent();
    assertEquals(500, testCourse.getEnrolledStudentCount());
  }

  @Test
  @Order(3)
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }


  @Test
  @Order(4)
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }


  @Test
  @Order(5)
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }


  @Test
  @Order(6)
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Adam Cannon");
    assertEquals("Adam Cannon", testCourse.getInstructorName());
  }


  @Test
  @Order(7)
  public void reassignLocationTest() {
    testCourse.reassignLocation("301 URIS");
    assertEquals("301 URIS", testCourse.getCourseLocation());
  }


  @Test
  @Order(8)
  public void reassignTimeTest() {
    testCourse.reassignTime("4:10-5:25");
    assertEquals("4:10-5:25", testCourse.getCourseTimeSlot());
  }


  @Test
  @Order(9)
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(200);
    Assertions.assertNotEquals(500, testCourse.getEnrolledStudentCount());
  }


  @Test
  public void isCourseFullTest() {
    assertEquals(true, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

