package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  /**
   * Sets up testDepartment for testing purposes.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4118", coms4118);
    testDepartment = new Department("COMS", courses, "Department Chair", 2);
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Department Chair", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4118", coms4118);
    assertEquals(courses, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(3, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(1, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTestFail() {
    testDepartment.dropPersonFromMajor();
    testDepartment.dropPersonFromMajor();
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("4156", "Gail Kaizer", "Remote", "10:10-11:25", 50);
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    Course testCourse = new Course("Gail Kaizer", "Remote", "10:10-11:25", 50);
    assertEquals(courses.get("4156"), testCourse);
  }

  @Test
  public void toStringTest() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    String str = "COMS 4118: " + coms4118.toString() + "\n";
    assertEquals(str, testDepartment.toString());
  }

  @Test
  public void equalsTest() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4118", coms4118);
    Department testEquals = new Department("COMS", courses, "Department Chair", 2);
    assertEquals(testDepartment, testEquals);
    Department testNotEqualsChair = new Department("COMS", courses, "None", 2);
    Department testNotEqualsNum = new Department("COMS", courses, "Department Chair", 0);
    assertNotEquals(testEquals, testNotEqualsChair);
    assertNotEquals(testEquals, testNotEqualsNum);
    assertNotEquals(testEquals, 1);
  }

  @Test
  public void hashCodeTest() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4118", coms4118);
    Department testEquals = new Department("COMS", courses, "Department Chair", 2);
    assertEquals(testDepartment.hashCode(), testEquals.hashCode());
  }

  /**
   * The test department instance used for testing.
   */
  public static Department testDepartment;
}

