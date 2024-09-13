package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Department class.
 * Tests the functionality of the Department class methods, including edge cases.
 */
public class DepartmentUnitTests {

  private Department comsDept;
  private Department econDept;
  private Course coms1004;
  private Course coms3134;

  @BeforeEach
  public void setUp() {
    // Set up COMS department with some courses
    coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);

    coms3134 = new Course("Brian Borowski", "301 URIS", "4:10-5:25", 250);
    coms3134.setEnrolledStudentCount(242);

    HashMap<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", coms1004);
    comsCourses.put("3134", coms3134);

    comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);

    // Set up ECON department with some courses
    Course econ1105 = new Course("Waseem Noor", "309 HAV", "2:40-3:55", 210);
    econ1105.setEnrolledStudentCount(187);

    Course econ2257 = new Course("Tamrat Gashaw", "428 PUP", "10:10-11:25", 125);
    econ2257.setEnrolledStudentCount(63);

    HashMap<String, Course> econCourses = new HashMap<>();
    econCourses.put("1105", econ1105);
    econCourses.put("2257", econ2257);

    econDept = new Department("ECON", econCourses, "Michael Woodford", 2345);
  }

  @Test
  public void testGetNumberOfMajors() {
    // Test that number of majors returns correctly
    assertEquals(2700, comsDept.getNumberOfMajors(), "Number of COMS majors should be 2700.");
    assertEquals(2345, econDept.getNumberOfMajors(), "Number of ECON majors should be 2345.");
  }

  @Test
  public void testAddPersonToMajor() {
    // Test adding a major
    comsDept.addPersonToMajor();
    assertEquals(2701, comsDept.getNumberOfMajors(), "Number of COMS majors should increase by 1.");
  }

  @Test
  public void testDropPersonFromMajor() {
    // Test dropping a major
    econDept.dropPersonFromMajor();
    assertEquals(2344, econDept.getNumberOfMajors(), "Number of ECON majors should decrease by 1.");
  }

  @Test
  public void testDropPersonFromMajorEdgeCase() {
    // Test dropping a major when majors is at zero
    Department smallDept = new Department("TEST", new HashMap<>(), "Test Chair", 0);
    smallDept.dropPersonFromMajor();
    assertEquals(0, smallDept.getNumberOfMajors(), "Number of majors should not go below 0.");
  }

  @Test
  public void testGetDepartmentChair() {
    // Test getting the department chair
    assertEquals("Luca Carloni", comsDept.getDepartmentChair(), "COMS department chair should be Luca Carloni.");
    assertEquals("Michael Woodford", econDept.getDepartmentChair(), "ECON department chair should be Michael Woodford.");
  }

  @Test
  public void testGetCourseSelection() {
    // Test getting the course selection
    HashMap<String, Course> comsCourses = comsDept.getCourseSelection();
    assertNotNull(comsCourses, "COMS course selection should not be null.");
    assertEquals(2, comsCourses.size(), "COMS should have 2 courses.");
    assertTrue(comsCourses.containsKey("1004"), "COMS should contain course 1004.");
    assertTrue(comsCourses.containsKey("3134"), "COMS should contain course 3134.");

    HashMap<String, Course> econCourses = econDept.getCourseSelection();
    assertNotNull(econCourses, "ECON course selection should not be null.");
    assertEquals(2, econCourses.size(), "ECON should have 2 courses.");
    assertTrue(econCourses.containsKey("1105"), "ECON should contain course 1105.");
    assertTrue(econCourses.containsKey("2257"), "ECON should contain course 2257.");
  }

  @Test
  public void testAddCourse() {
    // Test adding a new course
    Course newCourse = new Course("Test Instructor", "Test Location", "10:00-11:30", 100);
    comsDept.addCourse("9999", newCourse);

    HashMap<String, Course> courses = comsDept.getCourseSelection();
    assertEquals(3, courses.size(), "COMS should have 3 courses after adding.");
    assertTrue(courses.containsKey("9999"), "COMS should contain the newly added course.");
  }

  @Test
  public void testCreateCourse() {
    // Test creating and adding a new course
    econDept.createCourse("9999", "Test Instructor", "Test Location", "10:00-11:30", 100);

    HashMap<String, Course> courses = econDept.getCourseSelection();
    assertEquals(3, courses.size(), "ECON should have 3 courses after creating.");
    assertTrue(courses.containsKey("9999"), "ECON should contain the newly created course.");
  }

  @Test
  public void testToString() {
    // Test the toString method with actual data structure comparison
    String deptString = comsDept.toString();
    assertTrue(deptString.contains("1004"), "toString should contain course 1004.");
    assertTrue(deptString.contains("3134"), "toString should contain course 3134.");
  }

//  @Test
//  public void testAddPersonToMajorEdgeCase() {
//    // Edge case: Adding person to major should work with large numbers, but prevent overflow
//    Department largeDept = new Department("MATH", new HashMap<>(), "Chair C", Integer.MAX_VALUE - 1);
//    largeDept.addPersonToMajor();
//    assertEquals(Integer.MAX_VALUE, largeDept.getNumberOfMajors(), "Majors count should handle max value without overflow.");
//
//    largeDept.addPersonToMajor(); // Now, adding beyond the max
//    assertEquals(Integer.MAX_VALUE, largeDept.getNumberOfMajors(), "Majors should not exceed Integer.MAX_VALUE.");
//  }
}
