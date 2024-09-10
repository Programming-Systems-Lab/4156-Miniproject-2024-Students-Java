package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 * This class contains tests to verify the behavior and functionality of the Course class.
 * It uses Spring's testing framework to set up the environment and run the tests.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse.setEnrolledStudentCount(256);
  }

  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void isCourseFullTest() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.isCourseFull());
  }

  @Test
  public void enrollStudentTest() {
    boolean expectedResult = false;
    assertEquals(expectedResult, testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    boolean expectedResult = true;
    assertEquals(expectedResult, testCourse.dropStudent());
  }

  @Test
  public void reassignInstructorTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());

    testCourse.reassignInstructor("Spongebob Squarepants");
    expectedResult = "Spongebob Squarepants";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());

    testCourse.reassignLocation("Bikini Bottom");
    expectedResult = "Bikini Bottom";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());

    testCourse.reassignTime("10:00-12:00");
    expectedResult = "10:00-12:00";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

