package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Department class.
 * the tests ensures the correctness of the Department class implementation.
 */
@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentUnitTests {

  /**
   * Before each running each test method, initializes a Department instance for
   * testing purposes.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

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


    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);
    courses.put("3157", coms3157);
    courses.put("3203", coms3203);
    courses.put("3261", coms3261);
    courses.put("3251", coms3251);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  void constructorTest() {
    assertEquals(200,
        new Department("COMS", new HashMap<>(), "Luca Carloni", 200).getNumberOfMajors());

    // test invalid argument for number of majors
    assertEquals(0,
        new Department("COMS", new HashMap<>(), "Luca Carloni", -1).getNumberOfMajors());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  void getCourseSelectionTest() {
    assertEquals(6, testDepartment.getCourseSelection().size());
  }

  @Test
  public void addMajorTest() {
    testDepartment.addMajor();
    assertEquals(2701, testDepartment.getNumberOfMajors());
  }

  @Test
  public void decreaseMajorTest() {
    testDepartment.decreaseMajor();
    assertEquals(2699, testDepartment.getNumberOfMajors());

    // test when the number of majors is 0
    Department testDepartment2 = new Department("COMS", new HashMap<>(), "Luca Carloni", 0);
    testDepartment2.decreaseMajor();
    assertEquals(0, testDepartment2.getNumberOfMajors());
  }


  @Test
  void addCourseTest() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    coms4156.setEnrolledStudentCount(109);
    testDepartment.addCourse("4156", coms4156);

    assertTrue(testDepartment.getCourseSelection().containsKey("4156"));

  }

  @Test
  public void createCourseTest() {
    testDepartment.createCourse("3827", "Daniel Rubenstein", "207 Math", "10:10-11:25", 300);

    assertTrue(testDepartment.getCourseSelection().containsKey("3827"));
  }

  /** The test department instance used for testing. */
  private static Department testDepartment;
}
