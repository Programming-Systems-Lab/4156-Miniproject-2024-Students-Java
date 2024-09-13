package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Represents a unit test class for the Department class. This class tests various functionalities
 * of the Department class under wide variety of conditions and makes sure they work as expected.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Sets up a Department instance and its courses for testing purposes.
   * This method is executed once before all tests.
   */
  @BeforeAll
  public static void setupDeptForTesting() {
    courses = new HashMap<>();
    Course course1 = new Course("instructer1", "loc1", "10:00-11:00", 65);
    Course course2 = new Course("instructer2", "loc2", "15:00-17:00", 120);
    courses.put("c1", course1);
    courses.put("c2", course2);
    testDept = new Department("CSEE", courses, "Luis Gravano", 2);
  }

  @Test
  @Order(1)
  public void toStringTest() {
    String expectedResult =
        "CSEE c1: " + courses.get("c1").toString() + "\nc2: " + courses.get("c2").toString() + "\n";
    assertEquals(expectedResult, testDept.toString());
  }

  @Test
  @Order(2)
  public void getCourseSelectionTest() {
    assertEquals(courses, testDept.getCourseSelection());
  }

  @Test
  @Order(3)
  public void getNumberOfMajorsTest() {
    int majors = testDept.getNumberOfMajors();
    assertEquals(2, majors);
  }

  @Test
  @Order(4)
  public void getDepartmentChairTest() {
    String deptChair = testDept.getDepartmentChair();
    assertEquals("Luis Gravano", deptChair);
  }

  @Test
  @Order(5)
  public void dropPersonFromMajorTest() {
    testDept.dropPersonFromMajor();
    assertEquals(1, testDept.getNumberOfMajors());
    testDept.dropPersonFromMajor();
    assertEquals(0, testDept.getNumberOfMajors());
    testDept.dropPersonFromMajor();
    assertEquals(0, testDept.getNumberOfMajors());
  }

  @Test
  @Order(6)
  public void addPersonToMajorTest() {
    testDept.addPersonToMajor();
    assertEquals(1, testDept.getNumberOfMajors());
  }

  @Test
  @Order(7)
  public void createCourseTest() {
    Course courseToAdd = new Course("Gail Kaiser", "ZOOM", "10:10-11:20", 132);
    testDept.createCourse("c3", "Gail Kaiser", "ZOOM", "10:10-11:20", 132);
    assertEquals(courseToAdd.toString(), testDept.getCourseSelection().get("c3").toString());
  }

  /** The test course instance used for testing. */
  public static HashMap<String, Course> courses;

  public static Department testDept;
}
