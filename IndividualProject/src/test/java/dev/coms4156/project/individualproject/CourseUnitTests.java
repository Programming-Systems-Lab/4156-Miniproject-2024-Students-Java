package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** Unit tests for the Course class. */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  public static Course testCourse;

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Alan Chen", "417 IAB", "11:40-12:55", 240);
  }

  /** Tests toString(). */
  @Test
  public void toStringTest() {
    Course testCourse1 = new Course("A B", "120S", "11:20-12:00", 240);
    String expectedResult = "\nInstructor: A B; Location: 120S; Time: 11:20-12:00";
    assertEquals(expectedResult, testCourse1.toString());
  }

  /** Tests enrollStudent() (valid case). */
  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(239);
    assertTrue(testCourse.enrollStudent());
  }

  /** Tests enrollStudent() (full case). */
  @Test
  public void enrollStudentWhenFullTest() {
    testCourse.setEnrolledStudentCount(240);
    assertFalse(testCourse.enrollStudent());
  }

  /** Tests dropStudent() (valid case). */
  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(240);
    assertTrue(testCourse.dropStudent());
  }

  /** Tests dropStudent() (invalid case). */
  @Test
  public void dropStudentWhenEmptyTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  /** Tests setEnrolledStudentCount(). */
  @Test
  public void setEnrolledStudentCountValidTest() {
    testCourse.setEnrolledStudentCount(100);
  }

  /** Tests isCourseFull(). */
  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(240);
    assertTrue(testCourse.isCourseFull());

    testCourse.setEnrolledStudentCount(239);
    assertFalse(testCourse.isCourseFull());
  }

  /** Tests reassignInstructor(). */
  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("instructor1");
    assertEquals("instructor1", testCourse.getInstructorName());
  }

  /** Tests reassignLocation(). */
  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("location1");
    assertEquals("location1", testCourse.getCourseLocation());
  }

  /** Tests reassignTime(). */
  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("10:00-11:00");
    assertEquals("10:00-11:00", testCourse.getCourseTimeSlot());
  }
}
