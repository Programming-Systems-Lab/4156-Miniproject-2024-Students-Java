package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Department class.
 */
public class DepartmentUnitTests {

  private Department department;
  private HashMap<String, Course> courses;

  /**
   * Setup test environment.
   */
  @BeforeEach
  public void setup() {
    // Create some test courses
    Course course1 = new Course("Adam Cannon", "301 URIS", "9:00-10:15", 200);
    Course course2 = new Course("David Vallancourt", "1205 MUDD", "11:00-12:15", 120);

    courses = new HashMap<>();
    courses.put("COMS1004", course1);
    courses.put("ELEN1201", course2);

    // Initialize the department
    department = new Department("COMS", courses, "Adam Cannon", 500);
  }

  /**
   * Test getNumberOfMajors method.
   */
  @Test
  public void testGetNumberOfMajors() {
    assertEquals(500, department.getNumberOfMajors(), "Initial number of majors should be 500.");
  }

  /**
   * Test getDepartmentChair method.
   */
  @Test
  public void testGetDepartmentChair() {
    assertEquals("Adam Cannon",
        department.getDepartmentChair(), "Department chair should be Adam Cannon.");
  }

  /**
   * Test getCourseSelection method.
   */
  @Test
  public void testGetCourseSelection() {
    assertNotNull(department.getCourseSelection(), "Course selection should not be null.");
    assertEquals(2, department.getCourseSelection().size(),
        "There should be 2 courses in the department.");
  }

  /**
   * Test addPersonToMajor method.
   */
  @Test
  public void testAddPersonToMajor() {
    int initialMajors = department.getNumberOfMajors();
    department.addMajorToDepartment();
    assertEquals(initialMajors + 1, department.getNumberOfMajors(),
        "Number of majors should have incremented by 1.");
  }

  /**
   * Test dropPersonFromMajor method when majors are greater than zero.
   */
  @Test
  public void testDropPersonFromMajor() {
    int initialMajors = department.getNumberOfMajors();
    department.dropPersonFromMajor();
    assertEquals(initialMajors - 1, department.getNumberOfMajors(),
        "Number of majors should have decremented by 1.");
  }

  /**
   * Test dropPersonFromMajor method when no majors are left.
   */
  @Test
  public void testDropPersonFromMajorWhenZero() {
    department = new Department("ELEN", courses, "David Vallancourt", 0); // Set majors to zero
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      department.dropPersonFromMajor();
    });

    // Optionally, check the exception message
    assertEquals("Cannot drop majors, no majors left.", exception.getMessage());
  }

  /**
   * Test addCourse method.
   */
  @Test
  public void testAddCourse() {
    Course newCourse = new Course("Keren Bergman",
        "829 MUDD", "1:00-2:15", 150);
    department.addCourse("ELEN3401", newCourse);

    HashMap<String, Course> updatedCourses = department.getCourseSelection();
    assertTrue(updatedCourses.containsKey("ELEN3401"),
        "Course ELEN3401 should be added to the department.");
    assertEquals(3, updatedCourses.size(), "There should now be 3 courses in the department.");
  }

  /**
   * Test createCourse method.
   */
  @Test
  public void testCreateCourse() {
    department.createCourse("COMS3261", "Tony Dear",
        "301 URIS", "11:00-12:15", 300);

    HashMap<String, Course> updatedCourses = department.getCourseSelection();
    assertTrue(updatedCourses.containsKey("COMS3261"),
        "Course COMS3261 should be added to the department.");
    assertEquals(3, updatedCourses.size(), "There should now be 3 courses in the department.");
  }

  /**
   * Test toString method.
   */
  @Test
  public void testToString() {
    String result = department.toString();
    assertTrue(result.contains("COMS COMS1004"),
        "The department code and course ID should be included in the output.");
    assertTrue(result.contains("COMS ELEN1201"),
        "The department code and course ID should be included in the output.");
  }
}
