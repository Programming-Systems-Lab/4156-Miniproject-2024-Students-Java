package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class is a test for Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  /** The test course instance used for testing. */
  public static Department testDepartment;
  public static HashMap<String, Course> testCourses;

  /**
   * This function create courses and department before
   * each test method run in the class.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);

    testCourses = new HashMap<>();
    testCourses.put("1004", coms1004);

    testDepartment = new Department("COMS", testCourses, "Luca Carloni", 2700);
  }

  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    HashMap<String, Course> courses = testDepartment.getCourseSelection();

    // Ensure the course selection is not null
    assertNotNull(courses);

    // check if the department contains the expected course
    assertTrue(courses.containsKey("1004"));

    // check if the course matched
    assertEquals(courses.get("1004"), testCourses.get("1004"));
  }

  @Test
  public void addPersonToMajorTest() {
    int expectedResult = 2701;
    testDepartment.addPersonToMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    int expectedResult = 2699;
    testDepartment.dropPersonFromMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course coms3134 = new Course("Brian Borowski", "301 URIS",
        "4:10-5:25", 250);

    testDepartment.addCourse("3134", coms3134);

    HashMap<String, Course> courses = testDepartment.getCourseSelection();

    // Ensure the course selection is not null
    assertNotNull(courses);

    // check if the department contains the course we've just addes
    assertTrue(courses.containsKey("3134"));

    // check if the course metched
    assertEquals(coms3134, testCourses.get("3134"));
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("3134", "Brian Borowski",
        "301 URIS", "4:10-5:25", 250);

    HashMap<String, Course> courses = testDepartment.getCourseSelection();

    // Ensure the course selection is not null
    assertNotNull(courses);

    // check if the department contains the course we've just addes
    assertTrue(courses.containsKey("3134"));

    // check if the course metched
    Course coms3134 = new Course("Brian Borowski", "301 URIS",
        "4:10-5:25", 250);
    assertEquals(coms3134.getCourseLocation(), testCourses.get("3134").getCourseLocation());
    assertEquals(coms3134.getInstructorName(), testCourses.get("3134").getInstructorName());
    assertEquals(coms3134.getCourseTimeSlot(), testCourses.get("3134").getCourseTimeSlot());;
    assertEquals(coms3134.getEnrollmentCapacity(), testCourses.get("3134").getEnrollmentCapacity());
    assertEquals(coms3134.getEnrolledStudentCount(),
        testCourses.get("3134").getEnrolledStudentCount());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "COMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: "
        + "11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }
}
