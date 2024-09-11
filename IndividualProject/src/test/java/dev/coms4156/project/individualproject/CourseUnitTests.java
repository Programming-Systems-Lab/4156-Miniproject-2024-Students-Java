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
    testCourse = new Course("Gail Kaiser", "501 NWC", 
                                  "10:10-11:25", 120);
   //testCourse.setEnrolledStudentCount(110);
  }
  
  @Test
  public void dropStudentTest() {
    testCourse = new Course("Gail Kaiser", 
                            "501 NWC", "10:10-11:25", 120);
    boolean res = testCourse.dropStudent();
    assertEquals(false, res);
  }

  @Test
  public void enrollStudentTest() {
    testCourse = new Course("Gail Kaiser", 
                            "501 NWC", "10:10-11:25", 120);
    boolean res = testCourse.enrollStudent();
    assertEquals(true, res);
    testCourse = new Course("Gail Kaiser", 
                            "501 NWC", "10:10-11:25", 0);
    assertEquals(false, testCourse.enrollStudent());
  }

  @Test
  public void getCourseLocationTest() {
    testCourse = new Course("Gail Kaiser",
    "501 NWC", "10:10-11:25", 120);
    String res = testCourse.getCourseLocation();
    assertEquals("501 NWC", res);
  }

  @Test
  public void getInstructorNameTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    String res = testCourse.getInstructorName();
    assertEquals("Gail Kaiser", res);
  }

  @Test
  public void getCourseTimeSlotTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    String res = testCourse.getCourseTimeSlot();
    assertEquals("10:10-11:25", res);
  }

  @Test
  public void reassignInstructorTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    testCourse.reassignInstructor("TA");
    String res = testCourse.getInstructorName();
    assertEquals("TA", res);
  }

  @Test
  public void toStringTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    String expectedResult = 
    "\nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignLocationTest() {
    testCourse = new Course("Gail Kaiser",
    "501 NWC", "10:10-11:25", 120);
    testCourse.reassignLocation("Online");
    String res = testCourse.getCourseLocation();
    assertEquals("Online", res);
  }

  @Test
  public void reassignTimeTest() {
    testCourse = new Course("Gail Kaiser",
    "501 NWC", "10:10-11:25", 120);
    testCourse.reassignTime("9:10-10:25");
    String res = testCourse.getCourseTimeSlot();
    assertEquals("9:10-10:25", res);
  }

  @Test
  public void isCourseFullTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    assertEquals(false, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(120);
    assertEquals(true, testCourse.isCourseFull());
    testCourse.dropStudent();
    assertEquals(false, testCourse.isCourseFull());
    testCourse.enrollStudent();
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    testCourse = new Course("Gail Kaiser", 
    "501 NWC", "10:10-11:25", 120);
    testCourse.setEnrolledStudentCount(1000);
    assertEquals(false, testCourse.enrollStudent());
  }



  /** The test course instance used for testing. */
  public static Course testCourse;
}

