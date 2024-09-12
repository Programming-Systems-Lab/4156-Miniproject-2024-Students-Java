package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/** Unit tests for the Department class. */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  private static Department testDepartment;

  /** Setup method for creating initial test data. */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    Course course1 = new Course("Danny", "K st", "9:00-10:00", 30);
    Course course2 = new Course("Jenny", "L Ave", "10:00-11:00", 50);
    Map<String, Course> courses = new HashMap<>();
    courses.put("C101", course1);
    courses.put("C102", course2);
    testDepartment = new Department("CS", courses, "Bill", 200);
  }

  /** Test getNumberOfMajors(). */
  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(200, testDepartment.getNumberOfMajors());
  }

  /** Test getDepartmentChair(). */
  @Test
  public void getDepartmentChairTest() {
    assertEquals("Bill", testDepartment.getDepartmentChair());
  }

  /** Test getCourseSelection(). */
  @Test
  public void getCourseSelectionTest() {
    Map<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(4, courses.size());
    assertTrue(courses.containsKey("C101"));
    assertTrue(courses.containsKey("C102"));
  }

  /** Test addPersonToMajor(). */
  @Test
  public void addPersonToMajorTest() {
    int initialMajors = testDepartment.getNumberOfMajors();
    testDepartment.addPersonToMajor();
    assertEquals(initialMajors + 1, testDepartment.getNumberOfMajors());
  }

  /** Test dropping a person from the major. */
  @Test
  public void dropPersonFromMajorTest() {
    int initialMajors = testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    assertEquals(initialMajors - 1, testDepartment.getNumberOfMajors());
  }

  /** Test that dropping a person does not decrease the count below 0. */
  @Test
  public void dropPersonFromMajorNonNegativeTest() {
    Department smallDept = new Department("EE", new HashMap<>(), "Dr. Brown", 0);
    smallDept.dropPersonFromMajor();
    assertEquals(0, smallDept.getNumberOfMajors());
  }

  /** Test addCourse(). */
  @Test
  public void addCourseTest() {
    Course newCourse = new Course("Klain", "1st Ave", "9:00-16:00", 60);
    testDepartment.addCourse("4018", newCourse);
    assertTrue(testDepartment.getCourseSelection().containsKey("4018"));
    assertEquals(newCourse, testDepartment.getCourseSelection().get("4018"));
  }

  /** Test createCourse(). */
  @Test
  public void createCourseTest() {
    testDepartment.createCourse("5416", "Bob", "2nd St", "12:00-1:00", 40);
    assertTrue(testDepartment.getCourseSelection().containsKey("5416"));
    Course createdCourse = testDepartment.getCourseSelection().get("5416");
    assertEquals("Bob", createdCourse.getInstructorName());
    assertEquals("2nd St", createdCourse.getCourseLocation());
    assertEquals("12:00-1:00", createdCourse.getCourseTimeSlot());
  }

  /** Test toString(). */
  @Test
  public void toStringTest() {
    String expectedOutput =
        "CS C101: \n"
            + "Instructor: Danny; Location: K st; Time: 9:00-10:00\n"
            + "CS 4018: \n"
            + "Instructor: Klain; Location: 1st Ave; Time: 9:00-16:00\n"
            + "CS 5416: \n"
            + "Instructor: Bob; Location: 2nd St; Time: 12:00-1:00\n"
            + "CS C102: \n"
            + "Instructor: Jenny; Location: L Ave; Time: 10:00-11:00\n";
    assertEquals(expectedOutput, testDepartment.toString());
  }
}
