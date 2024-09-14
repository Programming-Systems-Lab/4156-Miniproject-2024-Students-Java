package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * This class implements Unit Tests for the {@link Department} class.
 *
 * <p>This includes testing the getNumberOfMajors() and getDepartmentMajors(), addPersonToMajor(),
 * dropPersonFromMajor(), addCourse(), createCourse(), and toString() methods.
 */

@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  private static Department originalCompSciDept;
  private static String departmentCode = "COMS";

  private Department compSciDept;

  @BeforeAll
  public static void setupDepartmentDataForTesting() {
    MyFileDatabase myFileDatabase = new MyFileDatabase(0, "./data.txt"); // Local variable
    originalCompSciDept = myFileDatabase.getDepartmentMapping().get(departmentCode);
  }


  /**
   * Generates a deep copy of the originalCompSciDept object for
   * debugging purposes. Enables changes to verify class methods.
   */
  @BeforeEach
  public void setupTestCourseForEachTest() {
    compSciDept = originalCompSciDept.cloneDepartment();
  }

  /**
   * This function tests the {@code getCourseSelection()} method.
   *
   * <p>In the implementation of this test, we create a hashmap of expected course based on
   * data.txt. We then retrieve the actual courses hashmap from {@code compSciDept} and convert
   * both to strings. Comparing resulting strings, we can determine if the results are consistent.
   */
  @Test
  public void testGetCourseSelection() {

    Map<String, Course> courses = new HashMap<>();

    // Add the course details to the map
    courses.put("3827", new Course("Daniel Rubenstein", "207 Math", "10:10-11:25", 300));
    courses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    courses.put("3203", new Course("Ansaf Salleb-Aouissi", "301 URIS", "10:10-11:25", 250));
    courses.put("4156", new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120));
    courses.put("3157", new Course("Jae Lee", "417 IAB", "4:10-5:25", 400));
    courses.put("3134", new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250));
    courses.put("3251", new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125));
    courses.put("3261", new Course("Josh Alman", "417 IAB", "2:40-3:55", 150));

    String expectedResult = courses.toString();
    String actualResult = compSciDept.getCourseSelection().toString();

    assertEquals(expectedResult, actualResult);
  }


  /**
   * This function tests the {@code getNumberOfStudentsInDepartment()} method.
   */
  @Test
  public void testNumberOfStudentsInDepartment() {
    int expectedResult = 2700;
    assertEquals(expectedResult, compSciDept.getNumberOfMajors());
  }

  /**
   * This function tests the {@code getDepartmentChair()} method.
   */
  @Test
  public void testGetDepartmentChair() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, compSciDept.getDepartmentChair());
  }

  /**
   * This function tests the {@code dropMajor()} method.
   *
   * <p>If the number of majors is greater than 0, this method should return true.
   */
  @Test
  public void dropMajorSuccess() {
    assertTrue(compSciDept.dropPersonFromMajor());
  }

  /**
   * This function tests the {@code dropMajor()} method.
   *
   * <p>If the number of majors is 0 or less, this method should return false.
   */
  @Test
  public void dropMajorFailure() {
    compSciDept.setNumberOfStudentsInDepartment(0);
    assertFalse(compSciDept.dropPersonFromMajor());
  }

  /**
   * This function tests the {@code addCourse()} method.
   */
  @Test
  public void addCourseTest() {

    Course testCourse = new Course("Nicholas Ching",
        "CSB 101", "9:00-10:30", 100);
    compSciDept.addCourse("CS101", testCourse);
    assertNotNull(compSciDept.getCourseSelection().get("CS101"));
  }



  @Test
  public void testToString() {

    String expectedResult =
        "COMS 3827: \n"
        + "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25; Capacity: 300\n"
        + "COMS 1004: \n"
        + "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55; Capacity: 400\n"
        + "COMS 3203: \n"
        + "Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25; Capacity: 250\n"
        + "COMS 4156: \n"
        + "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25; Capacity: 120\n"
        + "COMS 3157: \n"
        + "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25; Capacity: 400\n"
        + "COMS 3134: \n"
        + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25; Capacity: 250\n"
        + "COMS 3251: \n"
        + "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40; Capacity: 125\n"
        + "COMS 3261: \n"
        + "Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55; Capacity: 150\n";

    assertEquals(expectedResult, compSciDept.toString());
  }
}
