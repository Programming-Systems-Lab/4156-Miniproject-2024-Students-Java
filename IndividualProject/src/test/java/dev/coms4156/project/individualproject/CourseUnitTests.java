package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Provides unit test for testing implementations in the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testSmallCourse = new Course("Phillip Le", "451 CSB", "11:40-12:55", 1);
  }

  @Test
  public void enrollStudentTest() {
    assertEquals(true, testSmallCourse.enrollStudent());
    assertEquals(false, testSmallCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    testSmallCourse.enrollStudent();
    assertEquals(true, testSmallCourse.dropStudent());
    assertEquals(false, testSmallCourse.dropStudent());
  }

  @Test
  public void getCourseLocationTest() {
    String testCourseExpectedResult = "417 IAB";
    assertEquals(testCourseExpectedResult, testCourse.getCourseLocation());
  }
  
  @Test
  public void getInstructorNameTest() {
    String testCourseExpectedResult = "Phillip Le";
    assertEquals(testCourseExpectedResult, testSmallCourse.getInstructorName());
  }

  @Test 
  public void getCourseTimeSlotTest() {
    String testCourseExpectedResult = "11:40-12:55";
    assertEquals(testCourseExpectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test 
  public void reassignInstructorTest() {
    String initialResult = "Phillip Le";
    assertEquals(initialResult, testSmallCourse.getInstructorName());
    String newResult = "Griffin Newbold";
    testSmallCourse.reassignInstructor(newResult);
    assertEquals(newResult, testSmallCourse.getInstructorName());
  }
  
  @Test 
  public void reassignTimeTest() {
    String initialResult = "11:40-12:55";
    assertEquals(initialResult, testSmallCourse.getCourseTimeSlot());
    String newResult = "10:10-11:25";
    testSmallCourse.reassignTime(newResult);
    assertEquals(newResult, testSmallCourse.getCourseTimeSlot());
  }

  @Test 
  public void isCourseFullTest() {
    testSmallCourse.enrollStudent();
    assertEquals(true, testSmallCourse.isCourseFull());
    testSmallCourse.dropStudent();
    assertEquals(false, testSmallCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testSmallCourse;
}

