package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

/**
 * This class contains the tests for the {@link Department} class.
 * The tests verify the correctness of the Department class's methods.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * The test course instance used for testing.
   */
  public static Department testDepartment;

  /**
   * Initializes a Department instance used across all tests in this class.
   * This method runs once before all the test methods are executed.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55", "1:10-3:40"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS", "402 CHANDLER", "207 Math", "501 NWC"};

    //data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    coms3203.setEnrolledStudentCount(215);
    Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
    coms3261.setEnrolledStudentCount(140);
    Course coms3251 = new Course("Tony Dear", locations[3], times[4], 125);
    coms3251.setEnrolledStudentCount(99);
    Course coms3827 = new Course("Daniel Rubenstein", locations[4], times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    Course coms4156 = new Course("Gail Kaiser", locations[5], times[2], 120);
    coms4156.setEnrolledStudentCount(109);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("coms1004", coms1004);
    courses.put("coms3134", coms3134);
    courses.put("coms3157", coms3157);
    courses.put("coms3203", coms3203);
    courses.put("coms3261", coms3261);
    courses.put("coms3251", coms3251);
    courses.put("coms3827", coms3827);
    courses.put("coms4156", coms4156);


    testDepartment = new Department(
            "coms",
            courses,
            "Luca Carloni",
            1000);
  }

  /**
   * Tests the {@link Department#getNumberOfMajors()} method.
   */
  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(1000, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the {@link Department#getDepartmentChair()} method.
   */
  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  /**
   * Tests the {@link Department#getCourseSelection()} method.
   */
  @Test
  public void getCourseSelectionTest() {
    assertEquals("\nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55",
            testDepartment.getCourseSelection().get("coms1004").toString());
  }

  /**
   * Tests the {@link Department#addPersonToMajor()} method.
   */
  @Test
  public void addPersonToMajorTest() {
    setupCourseForTesting();
    testDepartment.addPersonToMajor();
    assertEquals(1001, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the {@link Department#dropPersonFromMajor()} method.
   */
  @Test
  public void dropPersonFromMajorTest() {
    setupCourseForTesting();
    testDepartment.dropPersonFromMajor();
    assertEquals(999, testDepartment.getNumberOfMajors());
  }

  /**
   * Tests the {@link Department#addCourse(String, Course)} method.
   */
  @Test
  public void addCourseTest() {
    setupCourseForTesting();
    Course coms4111 = new Course("Donald Ferguson", "309 HAV", "10:10-12:40", 400);
    testDepartment.addCourse("4111", coms4111);
    assertEquals("\nInstructor: Donald Ferguson; Location: 309 HAV; Time: 10:10-12:40",
            testDepartment.getCourseSelection().get("4111").toString());
  }

  /**
   * Tests the
   * {@link Department#createCourse(String, String, String, String, int)}
   * method.
   */
  @Test
  public void createCourseTest() {
    setupCourseForTesting();
    testDepartment.createCourse("4111", "Donald Ferguson", "309 HAV", "10:10-12:40", 400);
    assertEquals("\nInstructor: Donald Ferguson; Location: 309 HAV; Time: 10:10-12:40",
            testDepartment.getCourseSelection().get("4111").toString());
  }

  /**
   * Tests the {@link Department#toString()} method.
   */
  @Test
  public void toStringTest() {
    setupCourseForTesting();
    String expectedResult =
            "COMS 1004: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "COMS 3134: \nInstructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n"
            + "COMS 3157: \nInstructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n"
            + "COMS 3203: \nInstructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25\n"
            + "COMS 3261: \nInstructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n"
            + "COMS 3251: \nInstructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n"
            + "COMS 3827: \nInstructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n"
            + "COMS 4156: \nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n";
    testDepartment.createCourse("4111", "Donald Ferguson", "309 HAV", "10:10-12:40", 400);
    assertEquals("\nInstructor: Donald Ferguson; Location: 309 HAV; Time: 10:10-12:40",
            testDepartment.toString());
  }
}