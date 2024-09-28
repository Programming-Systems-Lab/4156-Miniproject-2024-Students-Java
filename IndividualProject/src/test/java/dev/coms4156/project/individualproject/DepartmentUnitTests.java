package dev.coms4156.project.individualproject;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


/**
 * Unit tests for the Department class.
 */

@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {
  /**
   * Test object for the Department class.
   */
  @BeforeEach
    public void setupDepartmentForTesting() {
    Map<String, Course> courses = new HashMap<>();
    courses.put("COMS1001", new Course("Professor Comp Sci Doe", "MUDD 457", "9:00-10:00", 25));
    testDepartment = new Department("COMS", courses, "Mrs. Department Chair", 8);
  }

  @Test
  public void testGetNumberOfMajors() {
    assertEquals(8, testDepartment.getNumberOfMajors());
  }

  @Test
  public void testGetDepartmentChair() {
    assertEquals("Mrs. Department Chair", testDepartment.getDepartmentChair());
  }

  @Test
  public void testGetCourseSelection() {
    assertEquals(1, testDepartment.getCourseSelection().size());
  }

  @Test
  public void testAddPersonToMajor() {
    testDepartment.addPersonToMajor();
    assertEquals(9, testDepartment.getNumberOfMajors());
  }

  @Test
  public void testDropPersonFromMajor() {
    testDepartment.dropPersonFromMajor();
    assertEquals(7, testDepartment.getNumberOfMajors());
  }

  /** The test department instance used for testing. */
  public static Department testDepartment;
}

