package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Provides unit test for testing implementations in the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  /**
   * Provides setup for unit testing, sets up a new COMS Department for testing.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    testDepartment = new Department("COMS", courses, "Phillip Le", 10);
  }

  @Test
  public void getFunctionsTest() {
    assertEquals(10, testDepartment.getNumberOfMajors());
    String expectedString = "Phillip Le";
    assertEquals(expectedString, testDepartment.getDepartmentChair());
    assertEquals(courses, testDepartment.getCourseSelection());
  }

  @Test
  public void addMajorTest() {
    int majorsCount = testDepartment.getNumberOfMajors();
    testDepartment.addMajor();
    assertEquals(majorsCount + 1, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropMajorTest() {
    int majorsCount = testDepartment.getNumberOfMajors();
    testDepartment.dropMajor();
    assertEquals(majorsCount - 1, testDepartment.getNumberOfMajors());
  }
  
  @Test
  public void createCourseTest() {
    testDepartment.createCourse("4995", "Hugh Thomas", "CSB 451", "4:10-5:25", 100);
    Map<String, Course> newCourseMap = testDepartment.getCourseSelection();
    assertEquals(true, newCourseMap.containsKey("4995"));
  }

  @Test 
  public void toStringTest() {
    System.out.println(testDepartment.toString());
  }
  
  /** The test course instance used for testing. */
  public static Department testDepartment;
  public static Map<String, Course> courses = new HashMap<>();
}

