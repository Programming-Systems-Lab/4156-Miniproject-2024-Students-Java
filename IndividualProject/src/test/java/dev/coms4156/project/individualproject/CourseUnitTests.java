package dev.coms4156.project.individualproject;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    testCourse.setEnrolledStudentCount(110);
  }

  @Test
  public void dropStudentTest() {
    boolean res = testCourse.dropStudent();
    assertEquals(true, res);
  }

  @Test
  public void enrollStudentTest() {
    boolean res = testCourse.enrollStudent();
    assertEquals(true, res);
  }

  @Test
  public void getCourseLocationTest() {
    String res = testCourse.getCourseLocation();
    assertEquals("501 NWC", res);
  }

  @Test
  public void getInstructorNameTest() {
    String res = testCourse.getInstructorName();
    assertEquals("Gail Kaiser", res);
  }

  @Test
  public void getCourseTimeSlotTest() {
    String res = testCourse.getCourseTimeSlot();
    assertEquals("10:10-11:25", res);
  }

  @Test
  public void reassignInstructorTest() {
    testCourse.reassignInstructor("TA");
    String res = testCourse.getInstructorName();
    assertEquals("TA", res);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignLocationTest() {
    testCourse.reassignLocation("Online");
    String res = testCourse.getCourseLocation();
    assertEquals("Online", res);
  }

  @Test
  public void reassignTimeTest() {
    testCourse.reassignTime("9:10-10:25");
    String res = testCourse.getCourseTimeSlot();
    assertEquals("9:10-10:25", res);
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(false, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(120);
    assertEquals(true, testCourse.isCourseFull());
    testCourse.dropStudent();
    assertEquals(false, testCourse.isCourseFull());
    testCourse.enrollStudent();
    assertEquals(true, testCourse.isCourseFull());
  }



  /** The test course instance used for testing. */
  public static Course testCourse;
}

