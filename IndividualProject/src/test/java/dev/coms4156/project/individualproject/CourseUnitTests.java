package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for source code.
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

  @Test
  public void enrollDropStudentTest() {
    int extraStudent = 251;
    while (extraStudent > 0) {
      testCourse.dropStudent();
      extraStudent--;
    }
    assertEquals(true, testCourse.isCourseFull());
    testCourse.enrollStudent();
    assertEquals(false, testCourse.isCourseFull());
  }

  @Test
  public void getReassignTest() {
    testCourse.reassignInstructor("Songwen Zhao");
    testCourse.reassignLocation("Mudd");
    testCourse.reassignTime("14:00-16:00");

    assertEquals("Mudd", testCourse.getCourseLocation());
    assertEquals("Songwen Zhao", testCourse.getInstructorName());
    assertEquals("14:00-16:00", testCourse.getCourseTimeSlot());

    testCourse.reassignInstructor("Griffin Newbold");
    testCourse.reassignLocation("417 IAB");
    testCourse.reassignTime("11:40-12:55");
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

