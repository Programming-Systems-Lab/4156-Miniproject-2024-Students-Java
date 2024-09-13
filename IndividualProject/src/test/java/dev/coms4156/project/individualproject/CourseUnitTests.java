package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*--------------------------------------------------------- */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/** 
 * This is the unit test class.
*/
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }
  
  @Test
  public void enrollAndDropStudentTest() {
    boolean result;
    for (int i = 1; i <= 250; ++i) {
      result = testCourse.enrollStudent();
      assertEquals(true, result);
    } 
    assertEquals(true, testCourse.isCourseFull());

    for (int i = 1; i <= 250; ++i) {
      result = testCourse.dropStudent();
      assertEquals(true, result);
    }
    assertEquals(false, testCourse.isCourseFull());
  }

  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    String newName = "Prof. Newbold";
    testCourse.reassignInstructor(newName);
    assertEquals(newName, testCourse.getInstructorName());
    testCourse.reassignInstructor("Griffin Newbold");
  }

  @Test
  public void reassignLocationTest() {
    String newLoc = "418 IAB";
    testCourse.reassignLocation(newLoc);
    assertEquals(newLoc, testCourse.getCourseLocation());
    testCourse.reassignLocation("417 IAB");
  }

  @Test
  public void reassignTimeTest() {
    String newTime = "1:10-3:40";
    testCourse.reassignTime(newTime);
    assertEquals(newTime, testCourse.getCourseTimeSlot());
    testCourse.reassignTime("11:40-12:55");
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse.setEnrolledStudentCount(0);
    assertEquals(false, testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

