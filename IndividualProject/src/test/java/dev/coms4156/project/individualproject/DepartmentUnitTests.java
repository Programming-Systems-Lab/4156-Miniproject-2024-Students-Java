package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map; 
import java.util.HashMap;


/**
 * This class contains unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration

public class DepartmentUnitTests {
  
  /** Sets up test Department instance used for testing. */
  @BeforeAll
  public static void setupDepartmentForTesting() {
    Map<String, Course> courses = new HashMap<String, Course>(); 
    testDepartment = new Department("COMS", courses, "Luca Carloni", 1);
  }

  @Test
  @Order(1)
  public void getNumberOfMajorsTest() {
    assertEquals(1, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(2)
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  @Order(3)
  public void getCourseSelectionTest() { // getCourseSelection returns a hashmap of courses
    assertEquals(2, testDepartment.getCourseSelection().size());
  }

  @Test
  @Order(4)
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();
    assertEquals(2, testDepartment.getNumberOfMajors());
  }

  @Test
  @Order(5)
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();
    assertEquals(1, testDepartment.getNumberOfMajors());
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

  //  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {

  @Test
  public void toStringTest() {
    String expectedResult = "COMS 3827:\n" +  
                            "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25" + 
                            "\nCOMS 3251:\n" +
                            "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n";

    assertEquals(expectedResult, testDepartment.toString());
  }


  /** The test Department instance used for testing. */
  public static Department testDepartment;
}
