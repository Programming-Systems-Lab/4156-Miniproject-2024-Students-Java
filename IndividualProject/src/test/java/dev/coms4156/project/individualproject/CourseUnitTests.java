package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  }

  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  @Order(2)
  public void enrollDropWhenFullTest() {
    testCourse.setEnrolledStudentCount(250);

    assertTrue(testCourse.isCourseFull());
    assertFalse(testCourse.enrollStudent());
    assertTrue(testCourse.dropStudent());
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  @Order(3)
  public void enrollDropWhenEmptyTest() {
    testCourse.setEnrolledStudentCount(0);

    assertFalse(testCourse.dropStudent());
    assertTrue(testCourse.enrollStudent());
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

  @Test
  @Order(4)
  public void enrollWhenOneSeatLeftTest() {
    testCourse.setEnrolledStudentCount(249);
    assertTrue(testCourse.enrollStudent());
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  @Order(5)
  public void dropWhenOneStudentLeftTest() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
    assertFalse(testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

