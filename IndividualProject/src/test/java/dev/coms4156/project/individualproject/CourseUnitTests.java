package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Represents a unit test class for the Course class. This class tests various functionalities of
 * the Course class under wide variety of conditions and makes sure they work as expected.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 1);
  }

  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  @Order(2)
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("Jennifer");
    assertEquals("Jennifer", testCourse.getInstructorName());
  }

  @Test
  @Order(3)
  public void reassignLocationTest() {
    testCourse.reassignLocation("250 MUDD");
    assertEquals("250 MUDD", testCourse.getCourseLocation());
  }

  @Test
  @Order(4)
  public void reassignTimeTest() {
    testCourse.reassignTime("12:00-13:10");
    assertEquals("12:00-13:10", testCourse.getCourseTimeSlot());
  }

  @Test
  @Order(5)
  public void enrollStudentTest() {
    boolean isEnrolled1 = testCourse.enrollStudent();
    assertTrue(isEnrolled1);
    boolean isEnrolled2 = testCourse.enrollStudent();
    assertFalse(isEnrolled2);
  }

  @Test
  @Order(5)
  public void dropStudentTest() {
    boolean isDrop1 = testCourse.dropStudent();
    assertTrue(isDrop1);
    boolean isDrop2 = testCourse.dropStudent();
    assertFalse(isDrop2);
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}
