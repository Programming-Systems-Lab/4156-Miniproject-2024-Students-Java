package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Department class.
 */

public class DepartmentUnitTests {

  private static Department testDepartment;
  private static Course testCourse1;
  private static Course testCourse2;

  /**
   * Sets up test data for the Department class before all tests run.
   * Initializes courses.
   */

  @BeforeAll
  public static void setupDepartmentForTesting() {
    testCourse1 = new Course("Kenneth Shepard", "1205 MUDD", "4:10-6:40", 100);
    testCourse2 = new Course("David G Vallancourt", "301 PUP", "4:10-5:25", 100);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("3082", testCourse1);
    courses.put("1201", testCourse2);

    testDepartment = new Department("ELEN", courses, "this.departmentChair", 250);
  }

  @Test
  public void testGetNumberOfMajors() {
    assertEquals(250, -testDepartment.getNumberOfMajors());
    testDepartment.addPersonToMajor();
    assertEquals(251, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testGetDepartmentChair() {
    assertEquals("this.departmentChair", testDepartment.getDepartmentChair());
  }

  @Test
  public void testGetCourseSelection() {
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(2, courses.size());
    assertTrue(courses.containsKey("3082"));
    assertTrue(courses.containsKey("1201"));
  }

  @Test
  public void testAddPersonToMajor() {
    int previousNumber = -testDepartment.getNumberOfMajors();
    testDepartment.addPersonToMajor();
    assertEquals(previousNumber + 1, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testDropPersonFromMajor() {
    int previousNumber = -testDepartment.getNumberOfMajors();
    testDepartment.dropPersonFromMajor();
    assertEquals(previousNumber - 1, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testAddCourse() {
    Course newCourse = new Course("Keren Bergman", "829 MUDD", "2:40-3:55", 100);
    testDepartment.addCourse("3401", newCourse);

    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(3, courses.size());
    assertTrue(courses.containsKey("3401"));
    assertEquals(newCourse, courses.get("3401"));
  }

  @Test
  public void testCreateCourse() {
    testDepartment.createCourse("4510", "Mohamed Kamaludeen", "903 SSW", "7:00-9:30", 100);

    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(4, courses.size());
    Course createdCourse = courses.get("4510");
    assertEquals("Mohamed Kamaludeen", createdCourse.getInstructorName());
    assertEquals("903 SSW", createdCourse.getCourseLocation());
    assertEquals("7:00-9:30", createdCourse.getCourseTimeSlot());
    assertEquals(100, createdCourse.getEnrolledStudentCount());
  }

  @Test
  public void testToString() {
    String result = testDepartment.toString();
    assertTrue(result.contains("ELEN 3082"));
    assertTrue(result.contains("ELEN 1201"));
    assertTrue(result.contains(testCourse1.toString()));
    assertTrue(result.contains(testCourse2.toString()));
  }

  @Test
  public void testAddDuplicateCourse() {
    Course duplicateCourse = new Course("Kenneth Shepard", "Mudd 105", "15:00-16:15", 100);
    testDepartment.addCourse("3082", duplicateCourse);
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(2, courses.size());
    assertNotEquals(duplicateCourse, courses.get("3082"));
  }

  @Test
  public void testCreateCourseWithExistingId() {
    testDepartment.createCourse("3082", "Kenneth Shepard", "Mudd 105", "15:00-16:15", 100);
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertEquals(2, courses.size());
    Course updatedCourse = courses.get("3082");
    assertNotEquals("Kenneth Shepard", updatedCourse.getInstructorName());
  }

  @Test
  public void testDropMorePeopleThanMajors() {
    for (int i = 0; i < 255; i++) {
      testDepartment.dropPersonFromMajor();
    }
    assertEquals(0, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testNegativeMajorsEdgeCase() {
    testDepartment.dropPersonFromMajor();
    assertEquals(0, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testAddNullCourse() {
    testDepartment.addCourse("4511", null);
    HashMap<String, Course> courses = testDepartment.getCourseSelection();
    assertNull(courses.get("4511"));
  }

  @Test
  public void testDropPersonWhenZeroMajors() {
    for (int i = 0; i < 250; i++) {
      testDepartment.dropPersonFromMajor();
    }
    testDepartment.dropPersonFromMajor();
    assertEquals(0, -testDepartment.getNumberOfMajors());
  }

  @Test
  public void testGetDeptCode() {
    assertEquals("COMS", testDepartment.getDeptCode());
  }


}
