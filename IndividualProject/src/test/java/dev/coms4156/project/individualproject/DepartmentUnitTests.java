package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit testing for the Department Class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    testFileDatabase = new MyFileDatabase(0, "./data.txt");
    HashMap<String, Department> departments = testFileDatabase.getDepartmentMapping();
    testDepartment = departments.get("COMS");
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
  public void addPersonToMajorTest() {
    int expectedResult = 2701;
    testDepartment.addPersonToMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    int expectedResult = 2700;
    testDepartment.dropPersonFromMajor();
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course testCourse = new Course("Daniel Baeur", "100 HAV", "10:10-11:25", 200);
    testDepartment.addCourse("3137", testCourse);
    HashMap<String, Course> testCourses = testDepartment.getCourseSelection();
    Course course = testCourses.get("3137");
    assertEquals(testCourse.toString(), course.toString());
  }

  /** The test Department instance used for testing. */
  public static Department testDepartment;
  public static MyFileDatabase testFileDatabase;
}
