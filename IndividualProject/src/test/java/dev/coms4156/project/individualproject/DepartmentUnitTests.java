package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test cases to check methods in the Department.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Sets up a course object coms1004, a testCourses map,
   * and a test department map before each test.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    coms1004 = new Course("Adam Cannon", "417 IAB",
                                  "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    testCourses = new HashMap<>();
    testCourses.put("1004", coms1004);
    testDepartment = new Department("COMS", testCourses,
                                    "Luca Carloni", 2700);
  }

  /**
   * Tests Department() constructor when course is not null.
   */
  @Test
  public void constructorTestCourseNotNull() {
    Course actualCourse = testDepartment.getCourseSelection().get("1004");

    assertTrue(testDepartment.getCourseSelection().containsKey("1004"));
    assertEquals(testCourses.size(), testDepartment.getCourseSelection().size());
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());

    String expectedDeptCode = "COMS";
    String expectedDepartmentChair = "Luca Carloni";
    int expectedNumberOfMajors = 2700;

    assertEquals(expectedDeptCode, testDepartment.getDeptCode());
    assertEquals(expectedDepartmentChair, testDepartment.getDepartmentChair());
    assertEquals(expectedNumberOfMajors, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests Department() constructor when course is null.
   */
  @Test
  public void constructorTestCourseNull() {
    testCourses = null;
    testDepartment = new Department("COMS", testCourses,
                                    "Luca Carloni", 2700);

    Map<String, Course> expectedCourses = new HashMap<>();
    assertEquals(expectedCourses, testDepartment.getCourseSelection());

    String expectedDeptCode = "COMS";
    assertEquals(expectedDeptCode, testDepartment.getDeptCode());

    String expectedDepartmentChair = "Luca Carloni";
    assertEquals(expectedDepartmentChair, testDepartment.getDepartmentChair());

    int expectedNumberOfMajors = 2700;
    assertEquals(expectedNumberOfMajors, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests getDeptCode() method.
   */
  @Test
  public void getDeptCodeTest() {
    String expectedDeptCode = "COMS";
    assertEquals(expectedDeptCode, testDepartment.getDeptCode());
  }

  /**
   * Tests getNumberOfMajors() method.
   */
  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests getDepartmentChair() method.
   */
  @Test
  public void getDepartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }

  /**
   * Tests getCourseSelection() method.
   */
  @Test
  public void getCourseSelectionTest() {
    Course actualCourse = testDepartment.getCourseSelection().get("1004");

    assertTrue(testDepartment.getCourseSelection().containsKey("1004"));
    assertEquals(testCourses.size(), testDepartment.getCourseSelection().size());
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }

  /**
   * Tests addPersonToMajor() method when number of majors is not negative.
   */
  @Test
  public void addPersonToMajorTestWhenNotNegative() {
    testDepartment.addPersonToMajor();
    int expectedResult = 2701;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests addPersonToMajor() method when number of majors is negative.
   */
  @Test
  public void addPersonToMajorTestWhenNegative() {
    testDepartment = new Department("COMS", testCourses,
                                    "Luca Carloni", -1);
    assertThrows(IllegalStateException.class, () -> testDepartment.addPersonToMajor());
  }

  /**
   * Tests dropPersonFromMajor() method when number of majors is positive.
   */
  @Test
  public void dropPersonFromMajorTestWhenPositive() {
    testDepartment.dropPersonFromMajor();
    int expectedResult = 2699;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests dropPersonFromMajor() method when number of majors is not positive.
   */
  @Test
  public void dropPersonFromMajorTestWhenNotPositive() {
    testDepartment = new Department("COMS", testCourses,
                                    "Luca Carloni", 0);
    assertThrows(IllegalStateException.class, () -> testDepartment.dropPersonFromMajor());
  }

  /**
   * Tests addCourse() method.
   */
  @Test
  public void addCourseTest() {
    Course coms3134 = new Course("Brian Borowski", "301 URIS",
                                 "4:10-5:25", 250);
    coms3134.setEnrolledStudentCount(242);

    testCourses.put("3134", coms3134);

    testDepartment.addCourse("3134", coms3134);

    assertTrue(testDepartment.getCourseSelection().containsKey("1004"));
    assertTrue(testDepartment.getCourseSelection().containsKey("3134"));
    assertEquals(testCourses.size(), testDepartment.getCourseSelection().size());

    Course actualCourse1004 = testDepartment.getCourseSelection().get("1004");
    Course actualCourse3134 = testDepartment.getCourseSelection().get("3134");

    assertNotNull(actualCourse1004);
    assertNotNull(actualCourse3134);
    assertEquals(coms1004.getCourseLocation(), actualCourse1004.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse1004.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse1004.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse1004.getEnrolledStudentCount());

    assertEquals(coms3134.getCourseLocation(), actualCourse3134.getCourseLocation());
    assertEquals(coms3134.getInstructorName(), actualCourse3134.getInstructorName());
    assertEquals(coms3134.getCourseTimeSlot(), actualCourse3134.getCourseTimeSlot());
    assertEquals(coms3134.getEnrolledStudentCount(), actualCourse3134.getEnrolledStudentCount());
  }

  /**
   * Tests createCourse() method.
   */
  @Test
  public void createCourseTest() {
    Course coms3134 = new Course("Brian Borowski", "301 URIS",
                                 "4:10-5:25", 250);
    //coms3134.setEnrolledStudentCount(242);

    testCourses.put("3134", coms3134);

    testDepartment.createCourse("3134", "Brian Borowski",
                                "301 URIS", "4:10-5:25", 250);

    assertTrue(testDepartment.getCourseSelection().containsKey("1004"));
    assertTrue(testDepartment.getCourseSelection().containsKey("3134"));
    assertEquals(testCourses.size(), testDepartment.getCourseSelection().size());

    Course actualCourse1004 = testDepartment.getCourseSelection().get("1004");
    Course actualCourse3134 = testDepartment.getCourseSelection().get("3134");

    assertNotNull(actualCourse1004);
    assertNotNull(actualCourse3134);
    assertEquals(coms1004.getCourseLocation(), actualCourse1004.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse1004.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse1004.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse1004.getEnrolledStudentCount());

    assertEquals(coms3134.getCourseLocation(), actualCourse3134.getCourseLocation());
    assertEquals(coms3134.getInstructorName(), actualCourse3134.getInstructorName());
    assertEquals(coms3134.getCourseTimeSlot(), actualCourse3134.getCourseTimeSlot());
    assertEquals(0, actualCourse3134.getEnrolledStudentCount());
  }

  /**
   * Tests toString() method.
   */
  @Test
  public void toStringTest() {
    String expectedResult = "COMS 1004: \nInstructor: Adam Cannon; "
        + "Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }

  /** The Department instance used for testing. */
  private Department testDepartment;

  /** The Course instance used for testing. */
  private Course coms1004;

  /** The Map instance used for testing. */
  private Map<String, Course> testCourses;
}
