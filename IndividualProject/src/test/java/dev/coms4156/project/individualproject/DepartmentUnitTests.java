package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
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
   * Set up a sample department with some courses before each test.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    HashMap<String, Course> courses = new HashMap<>();
    d_test = new Department("TEST", courses, "test chair", 0);

    courses.put("4701", new Course("Ansaf Salleb-Aouissi", "501 SCH", "10:10-11:25", 189));
    courses.put("4705", new Course("Daniel Bauer", "417 IAB", "10:10-12:40", 275));

    d_coms = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  /**
   * Test that the Department constructor creates a valid Department object.
   */
  @Test
  public void testDepartmentCreation() {
    assertNotNull(d_coms);
    assertEquals(2700, d_coms.getNumberOfMajors());
    assertEquals("Luca Carloni", d_coms.getDepartmentChair());
  }

  /**
   * Test the addition of a student to the department's majors.
   */
  @Test
  public void testAddPersonToMajor() {
    d_coms.addPersonToMajor();
    assertEquals(2701, d_coms.getNumberOfMajors());
  }

  /**
   * Test the dropping of a student from the department's majors.
   */
  @Test
  public void testDropPersonFromMajor() {
    d_coms.dropPersonFromMajor();
    assertEquals(2699, d_coms.getNumberOfMajors());
    d_test.dropPersonFromMajor();
    assertEquals(0, d_test.getNumberOfMajors());
  }

  /**
   * Test that a course can be added to the department.
   */
  @Test
  public void testAddCourse() {
    Course newCourse = new Course("Shree Nayar", "451 CSB", "10:10-11:25", 100);
    d_coms.addCourse("4731", newCourse);

    assertEquals(3, d_coms.getCourseSelection().size());
    assertEquals(newCourse, d_coms.getCourseSelection().get("4731"));
  }

  /**
   * Test that a course can be created and added to the department.
   */
  @Test
  public void testCreateCourse() {
    d_coms.createCourse("4118", "Jason Nieh", "501 NWC", "4:10-5:25", 100);

    assertEquals(3, d_coms.getCourseSelection().size());
    Course course = d_coms.getCourseSelection().get("4118");
    assertEquals("Jason Nieh", course.getInstructorName());
    assertEquals("501 NWC", course.getCourseLocation());
  }

  /**
   * Test the toString method for correct output.
   */
  @Test
  public void testToString() {
    String expectedResult = "COMS 4705: \nInstructor: Daniel Bauer; "
        + "Location: 417 IAB; Time: 10:10-12:40\n"
        + "COMS 4701: \nInstructor: Ansaf Salleb-Aouissi; Location: 501 SCH; Time: 10:10-11:25\n";
    assertEquals(expectedResult, d_coms.toString());
  }

  /**
   * Test that the number of majors cannot become negative when dropping a student.
   */
  @Test
  public void testDropStudentNegativeMajors() {
    Department smallDept = new Department("SMALL", new HashMap<>(), "small dept chair", 0);
    smallDept.dropPersonFromMajor();
    assertEquals(0, smallDept.getNumberOfMajors());
  }

  public static Department d_coms;
  public static Department d_test;
}
