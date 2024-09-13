package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

/**
 * This class contains all the unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    // following testing data taken from IndividualProjectApplication.java
    HashMap<String, Course> courses = new HashMap<>();
    Course coms1004 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    coms1004.setEnrolledStudentCount(249);
    courses.put("1004", coms1004);
    testDepartment = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  @Test
  public void getNumberOfMajorsTest() {
    int expectedResult = 2700;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDEpartmentChairTest() {
    String expectedResult = "Luca Carloni";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }

  /* The test department instance used for testing. */
  public static Department testDepartment;
}
