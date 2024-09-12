package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
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
  @BeforeEach
  public void setupDepartmentForTesting() {
    coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    coms3827 = new Course("Daniel Rubenstein", "207 Math", "8:40-9:55", 300);
    coms3827.setEnrolledStudentCount(283);
    coms4156 = new Course("Gail Kaiser", "501 NWC", "4:10-5:25", 120);
    coms4156.setEnrolledStudentCount(109);
    courses = new HashMap<>();
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
    assertEquals(pre + 1, post);
  }

  @Test
  public void dropPersonFromMajorTest() {
    int pre = testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    int post = testDepartment.getNumberOfMajors();
    assertEquals(pre - 1, post);
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
    return deptCode + ": " + departmentChair + "\n"
        + deptCode + " 3251: " + coms3251.toString() + "\n"
        + deptCode + " 3827: " + coms3827.toString() + "\n"
        + deptCode + " 4156: " + coms4156.toString() + "\n";
  }

  /**
   * The test department instance and test data used for unit testing.
   */
  public Department testDepartment;
  public HashMap<String, Course> courses;
  public Course coms3251;
  public Course coms3827;
  public Course coms4156;
  public static final String deptCode = "COMS";
  public static final String departmentChair = "Luca Carloni";
  public static final int numberOfMajors = 2700;
}