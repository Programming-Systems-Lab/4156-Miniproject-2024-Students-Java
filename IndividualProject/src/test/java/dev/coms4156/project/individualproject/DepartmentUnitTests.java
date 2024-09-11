package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Writes test case to check methods in the Department.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeEach
  public void setupDepartmentForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "\nCOMS 1004: Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testDepartment.toString());
  }


  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void getDepartmentChairTest() {
    String expectedResult = "Adam Cannon";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }


  @Test
  public void getCourseSelectionTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    HashMap<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);

    assertEquals(expectedResult, testDepartment.getCourseSelection());
  }


  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    int expectedResult = 2701;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    int expectedResult = 2699;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void addCourseTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);

    HashMap<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);
    expectedResult.put("3134", coms3134);

    testDepartment.addCourse("3134", coms3134);
    assertEquals(expectedResult, testDepartment.getCourseSelection());
  }


  @Test
  public void createCourseTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    coms3203.setEnrolledStudentCount(215);

    HashMap<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);
    expectedResult.put("3203", coms3203);

    testDepartment.createCourse("3203", "Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    assertEquals(expectedResult, testDepartment.getCourseSelection());
  }


  /** The test department instance used for testing. */
  public Department testDepartment;
}
