package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map; 
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
  
  /** Sets up test Department instance used for testing. */
  @BeforeEach
  public void setupDepartmentForTesting() {
    Map<String, Course> courses = new HashMap<String, Course>(); 
    testDepartment = new Department("COMS", courses, "Luca Carloni", 1);
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(1, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() { 
    assertEquals(0, testDepartment.getCourseSelection().size());
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(2, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
    testDepartment.dropPersonFromMajor();
    assertEquals(0, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    testDepartment.addCourse("3251", coms3251);
    assertEquals(1, testDepartment.getCourseSelection().size());
  }

  @Test
  public void createCourseTest() {
    // Course coms3827 = new Course("Daniel Rubenstein", "207 Math", "10:10-11:25", 300);
    testDepartment.createCourse("3827", "Daniel Rubenstein", "207 Math",
                           "10:10-11:25", 300);
    Course createdCourse = testDepartment.getCourseSelection().get("3827");
    assertEquals("Daniel Rubenstein", createdCourse.getInstructorName());
    assertEquals("207 Math", createdCourse.getCourseLocation());
    assertEquals("10:10-11:25", createdCourse.getCourseTimeSlot());
    
  }

  @Test
  public void toStringTest() {
    Course coms3251 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    testDepartment.addCourse("3251", coms3251);
    String expectedResult = "COMS 3251: \n" 
                            +
                            "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n";
    assertEquals(expectedResult.trim(), testDepartment.toString().trim());
  }

  /** The test Department instance used for testing. */
  public static Department testDepartment;
}
