package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
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

    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }


  @Test
  public void toStringTest() {
    String expectedResult = "COMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }


  @Test
  public void constructorTestCourseNotNull() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    Map<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);

    Map<String, Course> actualResult = testDepartment.getCourseSelection();
    Course actualCourse = actualResult.get("1004");

    String expectedDeptCode = "COMS";
    String expectedDepartmentChair = "Luca Carloni";
    int expectedNumberOfMajors = 2700;

    assertEquals(expectedDeptCode, testDepartment.getDeptCode());
    assertEquals(expectedDepartmentChair, testDepartment.getDepartmentChair());
    assertEquals(expectedNumberOfMajors, testDepartment.getNumberOfMajors());

    assertEquals(expectedResult.size(), actualResult.size());
    assertTrue(actualResult.containsKey("1004"));
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }


  @Test
  public void constructorTestCourseNull() {
    testDepartment = new Department("COMS", null, "Luca Carloni", 2700);

    Map<String, Course> expectedCourses = new HashMap<>();
    String expectedDeptCode = "COMS";
    String expectedDepartmentChair = "Luca Carloni";
    int expectedNumberOfMajors = 2700;

    assertEquals(expectedCourses, testDepartment.getCourseSelection());
    assertEquals(expectedDeptCode, testDepartment.getDeptCode());
    assertEquals(expectedDepartmentChair, testDepartment.getDepartmentChair());
    assertEquals(expectedNumberOfMajors, testDepartment.getNumberOfMajors());
  }


  @Test
  public void getDeptCodeTest() {
    String expectedDeptCode = "COMS";
    assertEquals(expectedDeptCode, testDepartment.getDeptCode());
  }


  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void getDepartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }


  @Test
  public void getCourseSelectionTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    Map<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);

    Map<String, Course> actualResult = testDepartment.getCourseSelection();
    Course actualCourse = actualResult.get("1004");

    assertEquals(expectedResult.size(), actualResult.size());
    assertTrue(actualResult.containsKey("1004"));
    assertNotNull(actualCourse);
    assertEquals(coms1004.getCourseLocation(), actualCourse.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse.getEnrolledStudentCount());
  }


  @Test
  public void addPersonToMajorTestWhenNotNegative() {
    testDepartment.addPersonToMajor();
    int expectedResult = 2701;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void addPersonToMajorTestWhenNegative() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    testDepartment = new Department("COMS", courses, "Luca Carloni", -1);
    assertThrows(IllegalStateException.class, () -> testDepartment.addPersonToMajor());
  }


  @Test
  public void dropPersonFromMajorTestWhenPositive() {
    testDepartment.dropPersonFromMajor();
    int expectedResult = 2699;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }


  @Test
  public void dropPersonFromMajorTestWhenNotPositive() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);

    Map<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);

    testDepartment = new Department("COMS", courses, "Luca Carloni", 0);
    assertThrows(IllegalStateException.class, () -> testDepartment.dropPersonFromMajor());
  }


  @Test
  public void addCourseTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);

    Map<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);
    expectedResult.put("3134", coms3134);

    testDepartment.addCourse("3134", coms3134);

    Map<String, Course> actualResult = testDepartment.getCourseSelection();
    Course actualCourse1004 = actualResult.get("1004");
    Course actualCourse3134 = actualResult.get("3134");

    assertEquals(expectedResult.size(), actualResult.size());
    assertTrue(actualResult.containsKey("1004"));
    assertTrue(actualResult.containsKey("3134"));
    assertNotNull(actualCourse1004);
    assertNotNull(actualCourse3134);
    assertEquals(coms1004.getCourseLocation(), actualCourse1004.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse1004.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse1004.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse1004.getEnrolledStudentCount());

    assertEquals(coms3134.getCourseLocation(), actualCourse3134.getCourseLocation());
    assertEquals(coms3134.getInstructorName(), actualCourse3134.getInstructorName());
    assertEquals(coms3134.getCourseTimeSlot(), actualCourse3134.getCourseTimeSlot());
    assertEquals(coms3134.getEnrolledStudentCount(), actualCourse3134.getEnrolledStudentCount());
  }


  @Test
  public void createCourseTest() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};

    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3203 = new Course("Ansaf Salleb-Aouissi", locations[2], times[2], 250);
    //coms3203.setEnrolledStudentCount(215);

    Map<String, Course> expectedResult = new HashMap<>();
    expectedResult.put("1004", coms1004);
    expectedResult.put("3203", coms3203);

    testDepartment.createCourse("3203", "Ansaf Salleb-Aouissi", locations[2], times[2], 250);

    Map<String, Course> actualResult = testDepartment.getCourseSelection();
    Course actualCourse1004 = actualResult.get("1004");
    Course actualCourse3203 = actualResult.get("3203");

    assertEquals(expectedResult.size(), actualResult.size());
    assertTrue(actualResult.containsKey("1004"));
    assertTrue(actualResult.containsKey("3203"));
    assertNotNull(actualCourse1004);
    assertNotNull(actualCourse3203);
    assertEquals(coms1004.getCourseLocation(), actualCourse1004.getCourseLocation());
    assertEquals(coms1004.getInstructorName(), actualCourse1004.getInstructorName());
    assertEquals(coms1004.getCourseTimeSlot(), actualCourse1004.getCourseTimeSlot());
    assertEquals(coms1004.getEnrolledStudentCount(), actualCourse1004.getEnrolledStudentCount());

    assertEquals(coms3203.getCourseLocation(), actualCourse3203.getCourseLocation());
    assertEquals(coms3203.getInstructorName(), actualCourse3203.getInstructorName());
    assertEquals(coms3203.getCourseTimeSlot(), actualCourse3203.getCourseTimeSlot());
    assertEquals(coms3203.getEnrolledStudentCount(), actualCourse3203.getEnrolledStudentCount());
  }


  /** The test department instance used for testing. */
  public Department testDepartment;
}
