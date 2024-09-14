package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Create variable for testing. Use physical dept and course objects to test the methods.
   */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    testCourse = new Course("Szabolcs Marka", "301 PUP", times[3], 150);
    testCourse.setEnrolledStudentCount(131);
    testCourse2 = new Course("Eric Raymer", "428 PUP", times[3], 145);
    testCourse2.setEnrolledStudentCount(130);
    testCourse3 = new Course("Kerstin M Perez", "428 PUP", times[2], 140);
    testCourse3.setEnrolledStudentCount(77);

    testCourseMap = new HashMap<>();
    testCourseMap2 = new HashMap<>();

    testCourseMap.put("1001", testCourse);
    testCourseMap.put("1201", testCourse2);
    testCourseMap2.put("1001", testCourse);

    testDept = new Department("PSYC", testCourseMap, "Nim Tottenham", 437);
    testDept2 = new Department("PSYC", testCourseMap, "Nim Tottenham", 437);
    testDept3 = new Department("PSYC", testCourseMap, "Nim Tottenham", 437);
    testDept4 = new Department("PSYC", testCourseMap, "Nim Tottenham", 437);
    testDept5 = new Department("PSYC", testCourseMap2, "Nim Tottenham", 437);

  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(437, testDept.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Nim Tottenham", testDept.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(testCourseMap, testDept.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    assertEquals(437, testDept2.getNumberOfMajors());
    testDept2.addPersonToMajor();
    assertEquals(438, testDept2.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    assertEquals(437, testDept3.getNumberOfMajors());
    testDept3.dropPersonFromMajor();
    assertEquals(436, testDept3.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    testDept3.addCourse("1602", testCourse3);
    HashMap<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1001", testCourse);
    expectedResult.put("1201", testCourse2);
    expectedResult.put("1602", testCourse3);
    assertEquals(expectedResult, testDept3.getCourseSelection());
  }

  @Test
  public void createCourseTest() {
    testDept4.createCourse("1602", "Kerstin M Perez", "428 PUP",  "10:10-11:25", 140);
    HashMap<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1001", testCourse);
    expectedResult.put("1201", testCourse2);
    expectedResult.put("1602", testCourse3);
    assertEquals(expectedResult.toString(), testDept2.getCourseSelection().toString());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "PSYC 1001: \n"
            + "Instructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55\n";
    assertEquals(expectedResult, testDept5.toString());
  }

  /** The test department instance and course hashmap used for testing. */
  public static Department testDept;
  public static Department testDept2;
  public static Department testDept3;
  public static Department testDept4;
  public static Department testDept5;
  /** The test course instance used for testing. */
  public static Course testCourse;
  public static Course testCourse2;
  public static Course testCourse3;
  public static HashMap<String, Course> testCourseMap;
  public static HashMap<String, Course> testCourseMap2;
}
