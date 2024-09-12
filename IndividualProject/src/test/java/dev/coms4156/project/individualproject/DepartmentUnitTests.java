package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

/**
 * Unit tests for methods of Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  /**
   * Initialize some department objects used for unit tests.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    String[] times = { "11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55" };
    String[] locations = { "417 IAB", "309 HAV", "301 URIS" };
    // data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);
    Course coms3157 = new Course("Jae Lee", locations[0], times[1], 400);
    coms3157.setEnrolledStudentCount(311);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2],
    250);
    coms3203.setEnrolledStudentCount(215);
    Course coms3261 = new Course("Josh Alman", locations[0], times[3], 150);
    coms3261.setEnrolledStudentCount(140);
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    coms3251.setEnrolledStudentCount(99);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", times[2], 300);
    coms3827.setEnrolledStudentCount(283);
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", times[2], 120);
    coms4156.setEnrolledStudentCount(109);
    testCourses = new HashMap<>();
    testCourses.put("1004", coms1004);
    testCourses.put("3134", coms3134);
    testCourses.put("3157", coms3157);
    testCourses.put("3203", coms3203);
    testCourses.put("3261", coms3261);
    testCourses.put("3251", coms3251);
    testCourses.put("3827", coms3827);
    testCourses.put("4156", coms4156);

    testCoursesNone = new HashMap<>();
    testCoursesNone2 = new HashMap<>();
    testCoursesNone3 = new HashMap<>();

    testCoursesOne = new HashMap<>();
    testCoursesOne.put("4156", coms4156);

    testDepart = new Department("COMS", testCourses, "Luca Carloni", 2700);
    testDepartMoreMajor = new Department("COMS", testCourses, "Luca Carloni",
        2700);
    testDepartLessMajor = new Department("COMS", testCourses, "Luca Carloni",
        2700);
    testDepartAddCourse = new Department("COMS", testCoursesNone, "Luca Carloni", 2700);
    testDepartExtraCourse = new Department("COMS", testCoursesNone3, "Luca Carloni", 2700);
    testDepartCreateCourse = new Department("PSYC", testCoursesNone2, "Nim Tottenham", 437);
    testDepartToString = new Department("COMS", testCoursesOne, "Luca Carloni", 2700);
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2700, testDepart.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepart.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    assertEquals(testCourses, testDepart.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartMoreMajor.addPersonToMajor();
    assertEquals(2701, testDepartMoreMajor.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartLessMajor.dropPersonFromMajor();
    assertEquals(2699, testDepartLessMajor.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4156", coms4156);
    testDepartAddCourse.addCourse("4156", coms4156);
    assertEquals(courses, testDepartAddCourse.getCourseSelection());
  }

  @Test
  public void addCourseDuplicateTest() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4156", coms4156);
    testDepartAddCourse.addCourse("4156", coms4156);
    assertEquals(courses, testDepartAddCourse.getCourseSelection());
  }

  @Test
  public void addCourseExtraTest() {
    Course coms4156 = new Course("Gail Kaiser", "501 NWC", "10:10-11:25", 120);
    Course coms3827 = new Course("Daniel Rubenstein", "207 Math", "10:10-11:25", 300);
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("4156", coms4156);
    courses.put("3827", coms3827);
    testDepartExtraCourse.addCourse("4156", coms4156);
    testDepartExtraCourse.addCourse("3827", coms3827);
    assertEquals(courses, testDepartExtraCourse.getCourseSelection());
  }

  @Test
  public void createCourseTest() {
    // Course psyc4493 = new Course("Jennifer Blaze", "200 SCH", "2:10-4:00", 15);
    // HashMap<String, Course> courses = new HashMap<>();
    // courses.put("4493", psyc4493);
    testDepartCreateCourse.createCourse("4493", "Jennifer Blaze", "200 SCH", "2:10-4:00", 15);
    assertTrue(testDepartCreateCourse.getCourseSelection().containsKey("4493"));
  }

  @Test
  public void toStringTest() {
    String expectedResult = "COMS 4156: \nInstructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n";
    assertEquals(expectedResult, testDepartToString.toString());
  }



  public static Department testDepart;
  public static Department testDepartMoreMajor;
  public static Department testDepartLessMajor;
  public static Department testDepartAddCourse;
  public static Department testDepartExtraCourse;
  public static Department testDepartCreateCourse;
  public static Department testDepartToString;
  public static HashMap<String, Course> testCourses;
  public static HashMap<String, Course> testCoursesNone;
  public static HashMap<String, Course> testCoursesNone2;
  public static HashMap<String, Course> testCoursesNone3;
  public static HashMap<String, Course> testCoursesOne;
}
