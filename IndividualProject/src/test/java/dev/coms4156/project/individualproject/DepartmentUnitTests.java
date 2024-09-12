package dev.coms4156.project.individualproject;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
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
  public void createCourseTest() {
    testDepartment.createCourse("4156", "Gail Kaizer", "Remote", "10:10-11:25", 50);
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    Course testCourse = new Course("Gail Kaizer", "Remote", "10:10-11:25", 50);
    assertEquals(courses.get("COMS4156"), testCourse);
  }

  @Test
  public void toStringTest() {
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    String str = "4118 :" + coms4118.toString() + "\n";
    assertEquals(str, testDepartment.toString());
  }

  @Test
  public void equalsTest(){
    Course coms4118 = new Course("Jason Nieh", "417 IAB", "4:10-5:25", 50);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4118", coms4118);
    Department testEquals = new Department("COMS", courses, "Department Chair", 2);
    assertEquals(testDepartment, testEquals);
  }

  /** The test department instance used for testing. */
  public static Department testDepartment;
}

