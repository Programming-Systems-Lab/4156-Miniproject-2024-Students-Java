package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
   * Sets up a Department instance for testing purposes.
   * This method creates a COMS department with 7 courses:
   * COMS1004, COMS3134, COMS3157, COMS3203, COMS3261, COMS3251, and COMS3827.
   * Each course is initialized with a specific instructor, location, time slot,
   * enrollment capacity, and current enrollment count.
   * The department is set up with "Luca Carloni" as the department chair
   * and 2700 as the number of majors.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

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
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    courses.put("3827", coms3827);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(2701, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajor() {
    testDepartment.dropPersonFromMajor();
    assertEquals(2699, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    testDepartment.addCourse("4156", coms4156);
    assertEquals(coms4156.toString(), testDepartment.getCourseSelection().get("4156").toString());
  }

  @Test
  public void createCourse() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    testDepartment.createCourse("4156", "Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    assertEquals(coms4156.toString(), testDepartment.getCourseSelection().get("4156").toString());
  }

  @Test
  public void toStringTest() {
    String expectedResult = """
            COMS 3827:\s
            Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25
            COMS 1004:\s
            Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
            COMS 3203:\s
            Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25
            COMS 3157:\s
            Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25
            COMS 3134:\s
            Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25
            COMS 3251:\s
            Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
            COMS 3261:\s
            Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55
            """;
    assertEquals(expectedResult, testDepartment.toString());
  }

  /** The test department instance used for testing. */
  public static Department testDepartment;
}
