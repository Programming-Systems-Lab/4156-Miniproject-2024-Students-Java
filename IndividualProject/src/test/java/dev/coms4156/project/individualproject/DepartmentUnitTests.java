package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for source code.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    HashMap<String, Course> courses = new HashMap<>();
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  public void createCourseToStringTest() {
    testDepartment.createCourse("1001", "Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    assertEquals("COMS 1001: \n" 
        + "Instructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n", 
        testDepartment.toString());
  }

  /** The test course instance used for testing. */
  public static Department testDepartment;
}

