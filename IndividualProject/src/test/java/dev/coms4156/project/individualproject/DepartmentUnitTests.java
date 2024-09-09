package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
  * Sets the enrollment count for a department based on the provided department code.
  *
  * @param deptCode The code of the department whose enrollment count is to be set.
  * @return A response entity indicating the result of the operation.
  */
  
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

@BeforeAll
  public static void setupDepartmentForTesting() {
      HashMap<String, Course> courses = new HashMap<>();
      courses.put("4156", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
      testDepartment = new Department("CS", courses, "Dean", 50);
  }

  @Test
  public void constructorTest() {
      // assertEquals("4156", testDepartment.getDeptCode());
      assertEquals("Dean", testDepartment.getDepartmentChair());
      assertEquals(50, testDepartment.getNumberOfMajors());
      assertEquals(1, testDepartment.getCourseSelection().size());
  }


  @Test
  public void getNumberOfMajorsTest() {
      assertEquals(50, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
      assertEquals("Dean", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
      assertEquals(1, testDepartment.getCourseSelection().size());
  }

  @Test
  public void addPersonToMajorTest() {
      testDepartment.addPersonToMajor();
      assertEquals(51, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
      testDepartment.dropPersonFromMajor();
      assertEquals(49, testDepartment.getNumberOfMajors());
  }


  @Test
  public void addCourseTest() {
      Course newCourse = new Course("Christian Lim", "CSB451", "17:40-18:55", 100);
      testDepartment.addCourse("4995", newCourse);
      assertEquals(2, testDepartment.getCourseSelection().size());
      assertTrue(testDepartment.getCourseSelection().containsKey("C4995"));
  }



  @Test
  public void createCourseTest() {
      testDepartment.createCourse("4119", "Henning Schulzrinne", "Pupin 301", "14:40-15:55", 200);
      assertEquals(2, testDepartment.getCourseSelection().size());
      assertTrue(testDepartment.getCourseSelection().containsKey("4119"));
  }

  @Test
  public void toStringTest() {
      String expectedOutput = "4156: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n" +
                              "4995: \nInstructor: Christian Lim; Location: CSB451; Time: 17:40-18:55\n" +
                              "4119: \nInstructor: Henning Schulzrinne; Location: Pupin 301; Time: 14:40-15:55\n";
      assertEquals(expectedOutput, testDepartment.toString());
  }

  /** The test course instance used for testing. */
  public static Department testDepartment;
}

