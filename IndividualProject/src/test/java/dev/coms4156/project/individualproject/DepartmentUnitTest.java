package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the IndividualProjectApplication class.
 *
 * <p>Tests include verifying various methods within the
 * Department classes.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTest {

  /**
   * Defines and initializes courses to a new test department.
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    coms3827 = new Course("Daniel Rubenstein", "207 Math", "8:40-9:55", 300);
    coms3827.setEnrolledStudentCount(283);
    coms4156 = new Course("Gail Kaiser", "501 NWC", "4:10-5:25", 120);
    coms4156.setEnrolledStudentCount(109);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    courses.put("4156", coms4156);

    testDepartment = new Department(deptCode, courses, departmentChair, numberOfMajors);
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(testDepartment.getNumberOfMajors(), numberOfMajors);
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals(testDepartment.getDepartmentChair(), departmentChair);
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(courses, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    int pre = testDepartment.getNumberOfMajors();
    testDepartment.addPersonToMajor();
    int post = testDepartment.getNumberOfMajors();
    assertEquals(++pre, post);
  }

  @Test
  public void dropPersonFromMajorTest() {
    int pre = testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    int post = testDepartment.getNumberOfMajors();
    assertEquals(--pre, post);
  }

  @Test
  public void addCourseTest() {
    Course newCourse = addCourseTestHelper();
    testDepartment.addCourse("3157", newCourse);
    HashMap<String, Course> newMap = testDepartment.getCourseSelection();
    assertTrue(newMap.containsKey("3157"));
    assertEquals(newCourse, newMap.get("3157"));
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("3203", "Ansaf Salleb-Aouissi", "301 URIS",
        "2:40-3:55", 250);
    HashMap<String, Course> newMap = testDepartment.getCourseSelection();
    assertTrue(newMap.containsKey("3203"));
  }

  @Test
  public void toStringTest() {
    String deptTestString = toStringTestHelper();
    String deptToString = testDepartment.toString();
    assertEquals(deptToString, deptTestString);
  }

  private Course addCourseTestHelper() {
    return new Course("Jae Lee", "820 MUDD", "10:10-11:25", 250);
  }

  private String toStringTestHelper() {
    return deptCode + " 3251: " + coms3251.toString() + "\n"
        + deptCode + " 3827: " + coms3827.toString() + "\n"
        + deptCode + " 4156: " + coms4156.toString() + "\n"
        + deptCode + " 3157: " + testDepartment.getCourseSelection().get("3157").toString() + "\n"
        + deptCode + " 3203: " + testDepartment.getCourseSelection().get("3203").toString() + "\n";
  }

  /**
   * The test department instance and test data used for unit testing.
   */
  public static Department testDepartment;
  public static Course coms3251;
  public static Course coms3827;
  public static Course coms4156;
  public static String deptCode = "COMS";
  public static String departmentChair = "Luca Carloni";
  public static int numberOfMajors = 2700;
  public static HashMap<String, Course> courses = null;
}